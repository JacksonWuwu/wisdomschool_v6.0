package cn.wstom.student.mapper;



import cn.wstom.student.entity.Deck;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
public interface DeckMapper extends BaseMapper<Deck> {

    public void deckDelete(@Param("deckId") String deckId);
}
