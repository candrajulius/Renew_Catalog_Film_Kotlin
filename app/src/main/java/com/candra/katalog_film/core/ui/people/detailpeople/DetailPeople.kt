package com.candra.katalog_film.core.ui.people.detailpeople

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.candra.katalog_film.R
import com.candra.katalog_film.core.data.source.remote.States
import com.candra.katalog_film.core.domain.model.Casting
import com.candra.katalog_film.core.domain.model.Crew
import com.candra.katalog_film.core.domain.model.DetailPeopleModel
import com.candra.katalog_film.core.utils.Constant
import com.candra.katalog_film.core.utils.Constant.EXTRA_CASTING
import com.candra.katalog_film.core.utils.Constant.EXTRA_CREW
import com.candra.katalog_film.core.utils.FormatContent
import com.candra.katalog_film.core.utils.Helper.contentImageForCrewAndCast
import com.candra.katalog_film.core.utils.Helper.isDarkMode
import com.candra.katalog_film.databinding.DetailLayoutPeopleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPeople: AppCompatActivity()
{
    private lateinit var binding: DetailLayoutPeopleBinding
    private val detailPeopleViewModel by viewModels<DetailPeopleViewModel>()
    private var peopleId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailLayoutPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMovie)
        setDetailPeopleByIdFromDetailActivity()
        setComponentView()
        binding.btnShare.setOnClickListener {
            shareDataPeople()
        }
    }

    private fun setComponentView(){
        binding.apply {
            toolbarMovie.setNavigationIcon(
                if (isDarkMode) R.drawable.ic_baseline_close_24
                else R.drawable.ic_baseline_close_24_black
            )
            toolbarMovie.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setDetailPeopleByIdFromDetailActivity(){
        intent.getIntExtra(Constant.POSITION,0).let {
            if (it == 1) getDetailPeopleCastingById()
            else getDetailPeopleCrewById()
        }
    }


    private fun getDetailPeopleCrewById(){
        intent.getParcelableExtra<Crew>(EXTRA_CREW)?.let {
            peopleId = it.id
            binding.apply {
                toolbarMovie.subtitle = resources.getString(R.string.about,it.name)
                riwayatPerfiliman.text = it.job
                namaOrang.text = it.name
                gambarOrangnya.contentImageForCrewAndCast(Constant.IMAGE_PATH2 + it.profilePath,this@DetailPeople)
                if (it.gender == 1){
                    gender.text = getString(R.string.female)
                }else if (it.gender == 2){
                    gender.text = getString(R.string.male)
                }
                reputasi.text = it.poupularity.toString()
            }
            detailPeopleViewModel.getDetailPeopleById(peopleId).observe(this){ statesPeople ->
                setDetailPeople(statesPeople)
            }
        }
    }

    private fun setDetailPeople(states: States<DetailPeopleModel>){
        binding.apply {
            when(states){
                is States.Loading -> showProgressBar(true)
                is States.Success -> {
                    showProgressBar(false)
                    if (states.data.birthDay != null){
                        tanggalKelahiran.text = FormatContent.parsingDateFormat(states.data.birthDay)
                    }else{
                        tanggalKelahiran.text = "Data is null"
                    }
                    lokasiKelahiran.text = states.data.location
                    biografiOrang.text = states.data.biografi
                }
                is States.Failed -> {
                    showProgressBar(false)
                    Log.d("DetailPeople", "setDetailPeople: ${states.message}")
                }
                else -> {
                    showProgressBar(false)
                    showEmptyData()
                }
            }
        }
    }

    private fun showProgressBar(isShow: Boolean){
        binding.apply {
            progressBarDetailPeople.visibility = if (isShow) View.VISIBLE else View.GONE
            scroolView.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }

    private fun showEmptyData(){
        binding.apply {
            scroolView.visibility =  View.GONE
            showEmpty.root.visibility = View.VISIBLE
        }
    }

    private fun getDetailPeopleCastingById(){
        binding.apply {
            intent.getParcelableExtra<Casting>(EXTRA_CASTING)?.let {
                toolbarMovie.subtitle = resources.getString(R.string.about,it.name)
                riwayatPerfiliman.text = it.character
                namaOrang.text = it.name
                gambarOrangnya.contentImageForCrewAndCast(Constant.IMAGE_PATH2 + it.profilePath,this@DetailPeople)
                if (it.gender == 1){
                    gender.text = getString(R.string.female)
                }else if (it.gender == 2){
                    gender.text = getString(R.string.male)
                }
                reputasi.text = it.popularity.toString()
                peopleId = it.id
                detailPeopleViewModel.getDetailPeopleById(peopleId).observe(this@DetailPeople)
                { statesPeople ->
                    setDetailPeople(statesPeople)
                }

            }
        }
    }

    private fun shareDataPeople(){
        binding.apply {
            val name = namaOrang.text.toString()
            val character = riwayatPerfiliman.text.toString()
            val reputation = reputasi.text.toString()
            val dateBirthday = tanggalKelahiran.text.toString()
            val genderText = gender.text.toString()
            val locationBirth = lokasiKelahiran.text.toString()
            val biography = biografiOrang.text.toString()

            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT,"Name : $name \n character: $character \n " +
                        "jumlah reputasi: $reputation \n tanggal kelahiran: $dateBirthday \n " +
                        "Gender: $genderText \n Lokasi Kelahiran: Location: $locationBirth \n " +
                        "Biografi: $biography")
            }.also { startActivity(it) }
        }
    }
}