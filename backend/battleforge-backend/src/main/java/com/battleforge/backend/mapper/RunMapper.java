package com.battleforge.backend.mapper;

import com.battleforge.backend.dto.HeroRunDto;
import com.battleforge.backend.dto.MonsterRunDto;
import com.battleforge.backend.dto.MoveDto;
import com.battleforge.backend.dto.MoveEffectDto;
import com.battleforge.backend.model.Hero;
import com.battleforge.backend.model.Monster;
import com.battleforge.backend.model.Move;
import com.battleforge.backend.model.MoveEffect;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RunMapper {

    MonsterRunDto toMonsterRunDto(Monster monster);

    @Mapping(source = "user.username", target = "username")
    HeroRunDto toHeroRunDto(Hero hero);

    MoveDto toMoveDto(Move move);

    MoveEffectDto toMoveEffectDto(MoveEffect moveEffect);

}
