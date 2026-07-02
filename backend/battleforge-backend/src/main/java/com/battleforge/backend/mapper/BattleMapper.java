package com.battleforge.backend.mapper;

import com.battleforge.backend.dto.HeroBattleStateDto;
import com.battleforge.backend.dto.MonsterBattleStateDto;
import com.battleforge.backend.model.HeroBattleState;
import com.battleforge.backend.model.MonsterBattleState;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RunMapper.class})
public interface BattleMapper {

    HeroBattleStateDto toHeroBattleStateDto(HeroBattleState state);

    MonsterBattleStateDto toMonsterBattleStateDto(MonsterBattleState state);

}
