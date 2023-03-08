package cn.wstom.admin.mapper;

import cn.wstom.admin.entity.Deck;

import org.apache.ibatis.annotations.Param;

/**
 *
 */
public interface DeckMapper extends BaseMapper<Deck> {

    public void deckDelete(@Param("deckId") String deckId);
}
