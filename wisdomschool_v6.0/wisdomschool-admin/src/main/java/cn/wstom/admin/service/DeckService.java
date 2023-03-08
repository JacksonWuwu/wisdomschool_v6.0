package cn.wstom.admin.service;


import cn.wstom.admin.entity.Deck;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface DeckService extends BaseService<Deck> {

    public void deckDelete(String deckId);
}
