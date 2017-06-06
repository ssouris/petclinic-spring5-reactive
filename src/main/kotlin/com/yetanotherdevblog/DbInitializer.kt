package com.yetanotherdevblog

import com.yetanotherdevblog.petclinic.model.Owner
import com.yetanotherdevblog.petclinic.model.Pet
import com.yetanotherdevblog.petclinic.model.PetType
import com.yetanotherdevblog.petclinic.model.Speciality
import com.yetanotherdevblog.petclinic.model.Vet
import com.yetanotherdevblog.petclinic.repositories.OwnersRepository
import com.yetanotherdevblog.petclinic.repositories.PetRepository
import com.yetanotherdevblog.petclinic.repositories.PetTypeRepository
import com.yetanotherdevblog.petclinic.repositories.SpecialityRepository
import com.yetanotherdevblog.petclinic.repositories.VetRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import reactor.core.Disposable
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.util.UUID

@Component
class DbInitializer(val petTypeRepository: PetTypeRepository,
                    val specialityRepository: SpecialityRepository,
                    val vetRepository: VetRepository,
                    val ownersRepository: OwnersRepository,
                    val petRepository: PetRepository): CommandLineRunner {

    override fun run(vararg args: String?) {

        val ownerId = UUID.fromString("5bead0d3-cd7b-41e5-b064-09f48e5e6a08").toString()
        val dogId = UUID.randomUUID().toString()

        petTypeRepository.deleteAll().subscribeOnComplete {
            val petTypes = listOf("cat", "lizard", "snake", "bird", "hamster", "dog")
                    .map { if (it == "dog") PetType(name=it, id = dogId) else PetType(name = it) }
            petTypeRepository.saveAll(petTypes)
                    .subscribe( null, null, { println("Added  PetTypes") })
        }


        specialityRepository.deleteAll().subscribeOnComplete {
            val specialities = listOf("radiology", "dentistry", "surgery")
                    .map {Speciality(name = it)}
            specialityRepository.saveAll(specialities)
                    .subscribe( null, null, { println("Added  Specialities") })
        }

        vetRepository.deleteAll().subscribeOnComplete {
            vetRepository.saveAll(listOf(
                    Vet(firstName = "James", lastName="Carter"),
                    Vet(firstName = "Helen", lastName="Leary", specialities = setOf("radiology")),
                    Vet(firstName = "Linda", lastName="Douglas", specialities = setOf("dentistry", "surgery")),
                    Vet(firstName = "Rafael", lastName="Ortega", specialities = setOf("surgery")),
                    Vet(firstName = "Henry", lastName="Stevens", specialities = setOf("radiology")),
                    Vet(firstName = "Sharon", lastName="Jenkins")))
                    .subscribe( null, null, { println("Added  Vets") })
        }

        ownersRepository.deleteAll().subscribeOnComplete {
            ownersRepository.saveAll(listOf(
                    Owner(firstName = "James", lastName="Carter",
                            telephone = "123", address = "123", city = "asd",
                            id = ownerId)))
                    .subscribe( null, null, { println("Added  Owners") })
        }

        petRepository.deleteAll().subscribeOnComplete {
            petRepository.saveAll(listOf(Pet(
                    name = "Some name",
                    birthDate = LocalDate.now(),
                    type = dogId,
                    owner = ownerId)))
                    .subscribe( null, null, { println("Added Pets") })
        }

    }

    /**
     * Subscribe onComplete only. Used by db populators.
     */
    private fun <T> Mono<T>.subscribeOnComplete(completeConsumer: () -> Unit) : Disposable {
        return this.subscribe(null, null, completeConsumer)
    }

}
