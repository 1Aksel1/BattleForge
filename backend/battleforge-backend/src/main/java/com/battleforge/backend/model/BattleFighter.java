package com.battleforge.backend.model;

import java.util.List;

public interface BattleFighter {

    Double getCurrentHp();

    void setCurrentHp(Double currentHp);

    Double getMaxHp();

    Double getAttack();

    void setAttack(Double attack);

    Double getDefense();

    void setDefense(Double defense);

    Double getMagic();

    void setMagic(Double magic);

    List<ActiveEffect> getActiveEffects();

}
