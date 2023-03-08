package cn.wstom.student.service;


import cn.wstom.student.entity.Deck;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface DeckService extends BaseService<Deck> {

    public void deckDelete(String deckId);
}
