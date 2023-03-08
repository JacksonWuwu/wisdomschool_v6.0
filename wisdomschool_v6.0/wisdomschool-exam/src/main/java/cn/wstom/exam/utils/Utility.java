package cn.wstom.exam.utils;

import cn.wstom.exam.entity.Individual.Individual;
import cn.wstom.exam.entity.Individual.QuestionArith;
import cn.wstom.exam.entity.Individual.QuestionData;
import cn.wstom.exam.entity.Individual.QuestionType;
import org.apache.log4j.Logger;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {
    static Logger logger = Logger.getRootLogger();

    /**
     * 把数据写到word中
     *
     * @param info     要写到word的所有内容，已经做好格式控制
     * @param fileName 文件路径 如：d:/test.doc
     */
    public static void writeDoc(String info, String fileName) {

        POIFSFileSystem fs = null;
        DirectoryEntry directory = null;
        ByteArrayInputStream bais = null;
        DocumentEntry de = null;
        FileOutputStream ostream = null;
        try {
            fs = new POIFSFileSystem();
            directory = fs.getRoot();


            byte[] message = info.getBytes();
            bais = new ByteArrayInputStream(message);// 将字节用输入流接收


            de = directory.createDocument("WordDocument", bais);

            // 2 写入到文件，并关闭
            ostream = new FileOutputStream(fileName);
            fs.writeFilesystem(ostream);

        } catch (Exception e) {
            logger.error("把数据写到word中:", e);
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                logger.error("ByteArrayInputStream关闭出错：", e);

            }
            try {
                ostream.close();
            } catch (IOException e) {
                logger.error("FileOutputStream 关闭出错！", e);
            }
        }
    }


    /**
     * 生成从[from, to] 变量之间的随机整数
     *
     * @param from 从0开始
     *             Integer
     * @param to   符合条件的试题的全部数量
     *             Integer
     * @return Integer 返回的是from到to之间的整数
     */
    public static int randomIntNumber1(int from, int to) {
        float a = from + (to - from) * (new Random().nextFloat());
        int b = (int) a;
        return ((a - b) > 0.5 ? 1 : 0) + b;
    }

    /**
     * 自己测试
     *
     * @param args
     */
    public static void main(String[] args) {

        //new Random().nextFloat()  0到1之间的float的数
	/*	System.out.println(new Random().nextFloat());
		int b=(int)4.8;
		System.out.println(b);
		for(int i=0;i<100;i++){
		int test=Utility.randomIntNumber1(0, 5);
		System.out.println(test);
		}*/

        List<Integer> tmpCh = new ArrayList<Integer>();
        tmpCh.add(1);
        tmpCh.add(2);
        tmpCh.add(3);
        tmpCh.add(9);
        for (int i = 0; i < 4; i++) {
            int test = Utility.getDifferentNumber(tmpCh, 0, 10);
            tmpCh.add(test);
        }
        for (int i = 0; i < tmpCh.size(); i++) {
//            System.out.println(tmpCh.get(i).longValue());
        }
    }

    /**
     * 生成从[0,end)的随机整数
     *
     * @param end Integer
     * @return Integer
     */
    public static int randomIntNumber1(int end) {
        return new Random().nextInt(end);
    }

    /**
     * 生成0-1的随机数。
     *
     * @return 随机数
     */
    public static float randomFloatNumber() {
        return new Random().nextFloat();
    }

    /**
     * 求一个Integer类型 的长度
     *
     * @param num Integer
     * @return Integer
     */
    public static int length(int num) {
        return Integer.toString(num).length();
    }

    public static boolean findKey(List<Integer> list, int randomNumber) {
        int length = list.size();
        for (int i = 0; i < length; i++) {
            if (list.get(i) == randomNumber) {
                return true;
            }
        }

        return false;
    }

    /**
     * 得到一个[from, to]区间并且与list中不重复的Integer数
     *
     * @param list List<Integer>
     * @param from Integer
     * @param to   Integer
     * @return Integer
     */
    public static int getDifferentNumber(List<Integer> list, int from, int to) {
        int number = randomIntNumber1(from, to);
        while (true) {
            if (!findKey(list, number)) {
                return number;
            } else {
                number = randomIntNumber1(from, to);


            }
        }
    }

    /**
     * 选择算子，使用轮盘赌算法选择出用来交叉的染色体
     *
     * @param population 存放染色体的数组
     * @return 被选中的染色体
     */
    public static Individual selection(Individual[] population) {

        Float r = new Random().nextFloat();
        Individual ind;
        double sum = 0;
        int tmp = -1;

        for (int i = 0; i < QuestionData.POP_NUM; i++) {

            sum = QuestionArith.add(sum, population[i].getPi());

            if (new BigDecimal(Double.toString(sum)).compareTo(new BigDecimal(
                    Float.toString(r))) == 1) {
                tmp = i;
                break;
            }
        }

        if (tmp == -1) {
            ind = population[QuestionData.POP_NUM];
        } else {
            ind = population[tmp];

        }
        return ind;
    }

    /**
     * 计算每个染色体的适应值（Pi)
     *
     * @param popNum     种群数
     * @param population 存放染色体数组
     * @param maxFitness 最大适应值
     */
    public void calcPi(int popNum, Individual[] population,
                       double maxFitness) {


        double denominator = 0.0;

        for (int i = 0; i < popNum; i++) {
            denominator = QuestionArith.add(denominator, QuestionArith.div(maxFitness,
                    population[i].getFitness(), 4));
        }
        for (int i = 0; i < popNum; i++) {
            double numerator = QuestionArith.div(maxFitness,
                    population[i].getFitness(), 4);
            double result = QuestionArith.div(numerator, denominator, 4);
            population[i].setPi(result);
        }
    }

    public static double calcMaxFitness(int popNum) {

        return 0;
    }

    /**
     * 交叉操作，对每一条染色体生成一个随机数   ，
     * 如果该随机数小于交叉概率PC就进行交叉操作否则不操作，
     * 与之匹配的染色体也是随机选择的，交叉的位置也是随机的
     *
     * @param finalPopulation
     */
    public static void crossover(Individual[] finalPopulation) {
        if (finalPopulation != null) {
            int length = finalPopulation.length;
            int size = finalPopulation[0].getChromosome().size();
            for (int i = 0; i < length; i++) {

                float f = randomFloatNumber();
                if (f < QuestionData.PC) {
                    Individual ind = finalPopulation[i];
                    int random = randomIntNumber1(length);
                    Individual ind2 = finalPopulation[random];
                    int crossoverLocation = randomIntNumber1(0, size - 1);// 交叉的位置
                    List<Integer> c1 = ind.getChromosome();
                    List<Integer> c2 = ind2.getChromosome();
                    for (int j = crossoverLocation; j < size; j++) {
                        Integer g1 = c1.get(j);
                        Integer g2 = c2.get(j);
                        c1.set(j, g2);
                        c2.set(j, g1);
                    }
                    //把重新得到的染色体保存回去
                    ind.setChromosome(c1);
                    ind.setChromosome(c2);
                }
            }
        }

    }

    /**
     * 变异操作，对每一条染色体的基因生成【0，1】的随机数，如果小于变异概率PM就有变异发生，
     *
     * @param questionCount   计算被选中的每种题型有多少道题(数据库中的,被优化过的)
     * @param userType        用户选择的题型和相应题型的数量
     * @param finalPopulation 染色体群
     */
    public static void mutation(Individual[] finalPopulation,
                                int[] questionCount, List<QuestionType> userType) {
        int typeLength = questionCount.length;//题型数量
        int to = 0;
        if (finalPopulation != null) {
            int length = finalPopulation.length;
            for (int i = 0; i < length; i++) {
                Individual ind = finalPopulation[i];
                List<Integer> chromosome = ind.getChromosome();
                int size = chromosome.size();
                for (int j = 0; j < size; j++) {
                    //染色体长度
                    float f = randomFloatNumber();
                    if (f < QuestionData.PM) {
                        int sum = 0;
                        int k = 0;//变异的是什么类型的试题
                        int typeCount = 0;
                        do {
                            typeCount = userType.get(k++).getNumber();
                            sum += typeCount;
                            if (j <= sum) {
                                break;
                            }
                        } while ((k - 1) < typeLength);
                        to = questionCount[k - 1] - 1;
                        int randomQuestion = randomIntNumber1(1, to);
                        chromosome.set(j, randomQuestion);

                    }
                }
                ind.setChromosome(chromosome);
            }
        }
    }


}
