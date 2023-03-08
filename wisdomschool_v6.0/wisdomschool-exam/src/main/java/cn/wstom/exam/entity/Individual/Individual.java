package cn.wstom.exam.entity.Individual;



import cn.wstom.exam.entity.MyQuestions;
import cn.wstom.exam.utils.Utility;
import org.apache.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 自适应遗传算法
 *
 * @author hzh
 */
public class Individual implements Serializable {

    private static final long serialVersionUID = 4162529888034588162L;
    public static Logger logger = Logger.getLogger(Individual.class);
    /**
     * 基因数量--试题总量
     */
    private static int geneCount;
    /**
     * 曝光度误差
     */
    private double exposal_error;
    /**
     * 难度分布误差
     */
    private double diff_distribute_error;
    /**
     * 适应度
     */
    private double fitness;
    /**
     * 相对适应度，轮盘赌算法用
     */
    private double pi;
    /**
     * 题型类型的数量
     */
    private int length;
    /**
     * 染色体
     */
    private List<Integer> chromosome;
    /**
     * 存入被选中的试题，与染色体对应
     */
    private List<MyQuestions> list;
    /**
     * 个体的标记
     */
    private static int flag = 0;

    public Individual() {

    }

    /**
     * 带参构造器
     *
     * @param typeName      List型数组，存放的是数据中所有的相应题型的试题
     * @param questionCount 每个题型对应有多少试题（和typeName对应）
     * @param QuestionType  存放用户要求的题型，和题型对应的数量
     * @param charpter      章节
     * @param level         难度级别
     */
    @SuppressWarnings("unchecked")
    public Individual(List[] typeName, int[] questionCount, List<QuestionType> QuestionType, String[] charpter,
                      int level) {

//        System.out.println("初始化个体第:" + flag + "个:");
        length = QuestionType.size();// 这3个参数的长度都是一样的 题型类型的数量
        // 给染色体（chromosome）初始化
        chromosome = new ArrayList<Integer>();
        for (int i = 0; i < length; i++) {
            //chromosome 染色体 -试题 questionCount[i]符合该类型题的全部试题数量  QuestionType.get(i).getNumber()选择的数量
            //如果选择的数量比试题的数量还多 已没办法满足需求  结束这个方法的调用
            if (QuestionType.get(i).getNumber() > questionCount[i]) {
                System.out.println("求大于供：停止");
                return;
            }
            initChromosome(chromosome, questionCount[i], QuestionType.get(i).getNumber());
        }
        // 数量和自己选择的数量一致 可能有重复试题
        geneCount = chromosome.size();
        // 调整被初始化的染色体，并计算Fitness
        resetIndiWithChro(typeName, questionCount, QuestionType, charpter, level);
        flag++;
    }

    /**
     * 使用简单的随机方法初始化染色体，染色体中有重复的基因
     *
     * @param chromosome  存放代表试题的编号――基因
     * @param qNumber     某种试题有多少道题 库存量
     * @param selectedNum 用户要求选中的试题数量
     */
    @SuppressWarnings("unchecked")
    private void initChromosome(List chromosome, int qNumber, int selectedNum) {
        int tmp = selectedNum;
        //循环的次数是用户选中的试题数量
        while (tmp-- != 0) {
            int select = Utility.randomIntNumber1(0, qNumber - 1);
            chromosome.add(select);
        }
//        System.out.println("基因编码:" + chromosome);
    }

    /**
     * 调整被初始化的染色体，并计算Fitness
     *
     * @param typeName      保存从数据库中所有试题(被优化过，章节和试题类型都符合要求)
     * @param questionCount 计算被选中的每种题型有多少道题(数据库中的,被优化过的)
     * @param QuestionType  用户选择的题型和相应题型的数量
     * @param charpter      章节ID
     * @param level         难度等级
     */
    public void resetIndiWithChro(List[] typeName, int[] questionCount,
                                  List<QuestionType> QuestionType, String[] charpter,
                                  int level) {
        // 去除染色体中的重复基因
        adjustChromosome(chromosome, questionCount, QuestionType);
        list = this.chromosomeDecode(chromosome, typeName, QuestionType);//把染色体解码成相应的试题
        fitness = this.calcualateFitness(charpter, level);
    }

    /**
     * 去除染色体中的重复基因
     *
     * @param chromosome    染色体-试题
     * @param questionCount 题库中某一种试题有多少道题
     * @param QuestionType  用户要求某一种试题有多少道题
     */
    public void adjustChromosome(List<Integer> chromosome, int[] questionCount,
                                 List<QuestionType> QuestionType) {
        int start = 0;
        int end = 0;
        int qNumber = 0;// 题库中有多少道试题
        for (int i = 0; i < length; i++) {//length题型种类数
            if (0 != i) {
                start = end;
            }
            end = QuestionType.get(i).getNumber() + start;
            qNumber = questionCount[i];
            for (int j = start; j < end; j++) {
                List<Integer> tmpCh = new ArrayList<Integer>();
                for (int t = start; t < end; t++) {
                    tmpCh.add(chromosome.get(t));
                }
                int tmp = chromosome.get(j);
                for (int k = j + 1; k < end; k++) {
                    if (tmp == chromosome.get(k)) {
                        int tmp1 = Utility.getDifferentNumber(tmpCh, 0,
                                qNumber - 1);
                        tmpCh.add(tmp1);
                        chromosome.set(j, tmp1);
                    }
                }
            }
	/*		for (int j = start; j < end; j++) {


				int tmp = chromosome.get(j);
				for (int k = j + 1; k < end; k++) {

				if (tmp == chromosome.get(k)) {

				tmp = Utility.getDifferentNumber(chromosome, 0,
				qNumber - 1);
				chromosome.set(j, tmp);

				}
		}
	}*/
        }
//        System.out.println("去掉重复基因后:" + chromosome);
    }

    /**
     * 把染色体解码成对应的试题，以便进行计算
     *
     * @param chromosome   LIst<integer> 染色体
     * @param typeName     List [] 存放的是数据中所有的相应题型的试题
     * @param QuestionType List<QuestionType>存放用户要求的题型，和题型对应的数量
     * @return List<questionvo>
     */

    @SuppressWarnings("unchecked")
    public List<MyQuestions> chromosomeDecode(List<Integer> chromosome,
                                              List[] typeName, List<QuestionType> QuestionType) {
        List<MyQuestions> list = new ArrayList<MyQuestions>();
        List<MyQuestions> questions;
        int start = 0;
        int end = 0;
        for (int i = 0; i < length; i++) {
            //每一种题型的试题
            questions = typeName[i];
            if (0 != i) {
                start = end;
            }
            end = QuestionType.get(i).getNumber() + start;
            for (int j = start; j < end; j++) {
                int tmp = chromosome.get(j);
                questions.get(tmp).setQuestionscore(QuestionType.get(i).getScore());
                list.add(questions.get(tmp));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            MyQuestions vo = list.get(i);
//            System.out.println("解码得到的题目:" + vo.getTitle());
        }
        return list;
    }

    /**
     * 计算适应度
     *
     * @param charpter 章节
     * @param level    难度等级
     * @return
     */

    public double calcualateFitness(String[] charpter, int level) {
        diff_distribute_error = this.calculateDifficult(list, level);
        exposal_error = this.calculateExposal(list);
        double fit = QuestionArith.add(QuestionArith.add(0, QuestionArith.mul(diff_distribute_error,
                QuestionData.W_DIFFERENT)), QuestionArith.mul(exposal_error, QuestionData.W_EXPOSAL));
//        System.out.println("根据难度分布差、曝光度误差得到适应度:" + fit);
        return fit;
    }

    /**
     * 计算曝光度
     *
     * @param list 存放被选中的试题
     * @return 所有题爆光度之和
     */
    public double calculateExposal(List<MyQuestions> list) {
        int size = list.size();
        double result = 0;
        for (int i = 0; i < size; i++) {
            MyQuestions vo = list.get(i);
            result += Double.parseDouble(vo.getQexposed())
                    / Double.parseDouble(vo.getQmaxexposure());
        }
        BigDecimal bigDecimal = new BigDecimal(Double.toString(result));
//        System.out.println("曝光度:" + bigDecimal.toEngineeringString());
        return result;
    }

    /**
     * 计算难度分布误差
     *
     * @param list  存放被选中试题
     * @param level 试卷难度等级
     * @return 难度分布误差
     */
    public double calculateDifficult(List<MyQuestions> list, int level) {
        int length = list.size();
        int size = list.size();
        int diff1 = 0;
        int diff2 = 0;
        int diff3 = 0;
        int diff4 = 0;
        int diff5 = 0;
        for (int i = 0; i < size; i++) {
            if (QuestionData.LEVEL1.equals(list.get(i).getDifficulty())) {
                diff1++;
            } else if (QuestionData.LEVEL2.equals(list.get(i).getDifficulty())) {
                diff2++;
            } else if (QuestionData.LEVEL3.equals(list.get(i).getDifficulty())) {
                diff3++;
            } else if (QuestionData.LEVEL4.equals(list.get(i).getDifficulty())) {
                diff4++;
            } else if (QuestionData.LEVEL5.equals(list.get(i).getDifficulty())) {
                diff5++;
            }
        }
        // 要求某一难度试题量
        double t1 = 0;
        double t2 = 0;
        double t3 = 0;
        double t4 = 0;
        double t5 = 0;

        switch (level) {
            case 1:
                t2 = (double) (length * QuestionData.DIFFICULT_LEVEL[0][0]);
                t3 = (double) (length * QuestionData.DIFFICULT_LEVEL[0][1]);
                t4 = (double) (length * QuestionData.DIFFICULT_LEVEL[0][2]);
                t5 = (double) (length * QuestionData.DIFFICULT_LEVEL[0][3]);
                t1 = length - t2 - t3 - t4 - t5;
                break;
            case 2:
                t2 = (double) (length * QuestionData.DIFFICULT_LEVEL[1][0]);
                t3 = (double) (length * QuestionData.DIFFICULT_LEVEL[1][1]);
                t4 = (double) (length * QuestionData.DIFFICULT_LEVEL[1][2]);
                t5 = (double) (length * QuestionData.DIFFICULT_LEVEL[1][3]);
                t1 = length - t2 - t3 - t4 - t5;
                break;
            case 3:
                t2 = (double) (length * QuestionData.DIFFICULT_LEVEL[2][0]);
                t3 = (double) (length * QuestionData.DIFFICULT_LEVEL[2][1]);
                t4 = (double) (length * QuestionData.DIFFICULT_LEVEL[2][2]);
                t5 = (double) (length * QuestionData.DIFFICULT_LEVEL[2][3]);
                t1 = length - t2 - t3 - t4 - t5;
                break;
            case 4:
                t2 = (double) (length * QuestionData.DIFFICULT_LEVEL[3][0]);
                t3 = (double) (length * QuestionData.DIFFICULT_LEVEL[3][1]);
                t4 = (double) (length * QuestionData.DIFFICULT_LEVEL[3][2]);
                t5 = (double) (length * QuestionData.DIFFICULT_LEVEL[3][3]);
                t1 = length - t2 - t3 - t4 - t5;
                break;
            case 5:
                t2 = (double) (length * QuestionData.DIFFICULT_LEVEL[4][0]);
                t3 = (double) (length * QuestionData.DIFFICULT_LEVEL[4][1]);
                t4 = (double) (length * QuestionData.DIFFICULT_LEVEL[4][2]);
                t5 = (double) (length * QuestionData.DIFFICULT_LEVEL[4][3]);
                t1 = length - t2 - t3 - t4 - t5;
                break;
            default:
                throw new IllegalArgumentException("试卷难度等级只能是1，2，或者3，4,5");
                // break;
        }

        double result = QuestionArith.add(QuestionArith.add(QuestionArith.add(QuestionArith.add(Math.abs(QuestionArith.div(QuestionArith.sub(diff1,    //Math.abs返回一个数字的绝对值
                t1), length, 2)), Math.abs(QuestionArith.div(QuestionArith.sub(diff2, t2),
                length, 2))), Math.abs(QuestionArith.div(QuestionArith.sub(diff3, t3), length,
                2))), Math.abs(QuestionArith.div(QuestionArith.sub(diff4, t4), length, 2))),
                Math.abs(QuestionArith.div(QuestionArith.sub(diff5, t5), length, 2)));
//        System.out.println("难度分布差:" + result);
        return result;//最大值1.8
    }

    /**
     * @param bestrow_error         覆盖度误差
     * @param diff_distribute_error 难度分布误差
     * @param exposal_error         曝光度
     * @return
     */
    public double calcualateFitness(double bestrow_error,
                                    double diff_distribute_error, double exposal_error) {

        return QuestionArith.add(QuestionArith.add(bestrow_error * QuestionData.W_BESTROW,
                diff_distribute_error * QuestionData.W_DIFFERENT), exposal_error
                * QuestionData.W_EXPOSAL);
    }


    public double getExposal_error() {
        return exposal_error;
    }

    public void setExposal_error(double exposalError) {
        exposal_error = exposalError;
    }

    public double getDiff_distribute_error() {
        return diff_distribute_error;
    }

    public void setDiff_distribute_error(double diffDistributeError) {
        diff_distribute_error = diffDistributeError;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getPi() {
        return pi;
    }

    public void setPi(double pi) {
        this.pi = pi;
    }

    public List<MyQuestions> getList() {
        return list;
    }

    public void setList(List<MyQuestions> list) {
        this.list = list;
    }

    public List<Integer> getChromosome() {
        return chromosome;
    }

    public void setChromosome(List<Integer> chromosome) {
        this.chromosome = chromosome;
    }

    public static void main(String[] args) {
        logger.info(Double.parseDouble("10"));
        logger.info(QuestionArith.div(10.0, 3.0, 7));
//        System.out.println(Double.parseDouble("10"));
//        System.out.println(QuestionArith.div(10.0, 3.0, 7));
    }

    public Object deepClone() {
        ObjectInputStream oi = null;
        Object obj = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(this);
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
            logger.error("克隆出错：", e);
        }

        return (obj);
    }

}
