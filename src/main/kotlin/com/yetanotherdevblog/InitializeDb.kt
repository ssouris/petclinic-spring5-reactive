package com.yetanotherdevblog

import com.yetanotherdevblog.petclinic.model.PetType
import com.yetanotherdevblog.petclinic.model.Speciality
import com.yetanotherdevblog.petclinic.model.Vet
import com.yetanotherdevblog.petclinic.repositories.PetTypeRepository
import com.yetanotherdevblog.petclinic.repositories.SpecialityRepository
import com.yetanotherdevblog.petclinic.repositories.VetRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class InitializeDb(val petTypeRepository: PetTypeRepository,
                   val specialityRepository: SpecialityRepository,
                   val vetRepository: VetRepository): CommandLineRunner {

    override fun run(vararg args: String?) {

        petTypeRepository.deleteAll().subscribe(null, null, {
            val petTypes = listOf("cat", "dog", "lizard", "snake", "bird", "hamster")
                    .map { PetType(name = it) }
            petTypeRepository.saveAll(petTypes).subscribe( null, null, { println("Added  PetTypes") })
        })


        specialityRepository.deleteAll().subscribe(null, null, {
            val specialities = listOf("radiology", "dentistry", "surgery")
                    .map {Speciality(name = it)}
            specialityRepository.saveAll(specialities).subscribe( null, null, { println("Added  Specialities") })
        })

        vetRepository.deleteAll().subscribe(null, null, {
            vetRepository.saveAll(listOf(
                    Vet(firstName = "James", lastName="Carter"),
                    Vet(firstName = "Helen", lastName="Leary", specialities = setOf("radiology")),
                    Vet(firstName = "Linda", lastName="Douglas", specialities = setOf("dentistry", "surgery")),
                    Vet(firstName = "Rafael", lastName="Ortega", specialities = setOf("surgery")),
                    Vet(firstName = "Henry", lastName="Stevens", specialities = setOf("radiology")),
                    Vet(firstName = "Sharon", lastName="Jenkins")))
                    .subscribe( null, null, { println("Added  Vets") })

        })



    }


}
