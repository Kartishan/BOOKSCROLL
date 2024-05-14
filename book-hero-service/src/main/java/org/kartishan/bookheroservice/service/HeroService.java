package org.kartishan.bookheroservice.service;

import lombok.AllArgsConstructor;
import org.kartishan.bookheroservice.model.Hero;
import org.kartishan.bookheroservice.model.HeroDTO;
import org.kartishan.bookheroservice.model.HeroSimpleDTO;
import org.kartishan.bookheroservice.repository.HeroRepository;
import org.kartishan.bookheroservice.request.HeroRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

//TODO Подумать про названия DTO и в целом над хранением.

@Service
@AllArgsConstructor
public class HeroService {
    private final HeroRepository heroRepository;

    public Hero addHero(HeroRequest heroRequest) {
        Hero hero = new Hero();
        hero.setName(heroRequest.getName());
        hero.setGender(heroRequest.getGender());
        hero.setShortDescription(heroRequest.getShortDescription());
        hero.setPhysicalDescription(heroRequest.getPhysicalDescription());
        hero.setEducation(heroRequest.getEducation());
        hero.setOccupation(heroRequest.getOccupation());
        hero.setCharacter(heroRequest.getCharacter());
        hero.setMainCharacter(heroRequest.isMainCharacter());
        hero.setBookId(heroRequest.getBookId());
        if (heroRequest.getParentIds() != null && !heroRequest.getParentIds().isEmpty()) {
            Set<Hero> parents = heroRequest.getParentIds().stream()
                    .map(parentId -> heroRepository.findById(parentId)
                            .orElseThrow(() -> new RuntimeException("Родитель с id " + parentId + " не найден.")))
                    .collect(Collectors.toSet());

            hero.setParents(parents);
        }
        return heroRepository.save(hero);
    }

    public List<HeroDTO> findAllHeroesByBookId(UUID bookId) {
        List<Hero> heroes = heroRepository.findByBookId(bookId);
        return heroes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public HeroDTO convertToDto(Hero hero) {
        Set<HeroSimpleDTO> parentDtos = hero.getParents().stream()
                .map(this::convertToSimpleDto)
                .collect(Collectors.toSet());

        Set<HeroSimpleDTO> childrenDtos = heroRepository.findChildrenByHeroId(hero).stream()
                .map(this::convertToSimpleDto)
                .collect(Collectors.toSet());

        return HeroDTO.builder()
                .name(hero.getName())
                .gender(hero.getGender())
                .shortDescription(hero.getShortDescription())
                .physicalDescription(hero.getPhysicalDescription())
                .education(hero.getEducation())
                .occupation(hero.getOccupation())
                .character(hero.getCharacter())
                .isMainCharacter(hero.isMainCharacter())
                .parents(parentDtos)
                .children(childrenDtos)
                .build();
    }

    private HeroSimpleDTO convertToSimpleDto(Hero hero) {
        return HeroSimpleDTO.builder()
                .id(hero.getId())
                .name(hero.getName())
                .build();
    }
}
