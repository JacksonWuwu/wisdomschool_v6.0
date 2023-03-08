package cn.wstom.exam.entity.Individual;

/**
 * 题目的基本属性
 */
public class QuestionData {
    /**
     * 种群初始数量为50个
     */
    public static final int POP_NUM = 1000;
    /**
     * 后期种群
     */
    public static final int FINAL_POP_NUM = 500;
    /**
     * 交叉率
     */
    public static final float PC = 0.75f;
    /**
     * 变异率 0.05~0.1比较适合
     */
    public static final float PM = 0.1f;

    /**
     *     定义难度等级，五个难度的试题所占试卷的比例
     */
    public static final double[][] DIFFICULT_LEVEL = new double[][]{
            {0.2, 0.2, 0.2, 0.1}, {0.3, 0.2, 0.2, 0.1}, {0.2, 0.3, 0.2, 0.1}, {0.2, 0.2, 0.3, 0.1}, {0.2, 0.2, 0.2, 0.3}};

    /**
     * 试卷难度五个等级 容易,较易,一般,较难,困难
     */
    public static final String LEVEL1 = "1";
    public static final String LEVEL2 = "2";
    public static final String LEVEL3 = "3";
    public static final String LEVEL4 = "4";
    public static final String LEVEL5 = "5";
    /**
     * 难度覆盖度权重
     */
    public static final int W_BESTROW = 10;
    /**
     * 难度权重
     */
    public static final int W_DIFFERENT = 6;
    /**
     * 曝光度权重
     */
    public static final int W_EXPOSAL = 4;

}
