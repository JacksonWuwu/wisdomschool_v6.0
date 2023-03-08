package cn.wstom.exam.test;

import java.util.Random;

class GA1 {
    public static void main(String[] args) {

        //开始时间
        long startTime = System.currentTimeMillis();

        int N = 3;//维度
        int NP = 500;//种群规模
        int SIZE = 3; //因为找三个所以数基因数为3
        int maxGen = 100;//迭代次数
        double CR = 0.8;//交叉概率
        double MU = 0.02;//变异概率
        double MAX = 10;//上限
        double MIN = -10;//下限
        double[][] init_population = new double[NP][SIZE];//初始化种群
        double[][] select_population = new double[NP][SIZE];//选择后的优质种群
        double[][] cr_population = new double[NP][SIZE];//交叉后种群
        double[][] mu_population = new double[NP][SIZE];//变异后种群
        double best_Y = Integer.MAX_VALUE;
        double[] best = new double[maxGen];//最优解

        double[] Y = new double[NP]; //对应种群中每个个体的适应度
        double[] addSumP = new double[NP];//每个个体被选中的概率累加和
        Random random = new Random();

        //1.初始化种群
        for (int i = 0; i < NP; i++) {
            for (int j = 0; j < SIZE; j++) {
                init_population[i][j] = MIN + random.nextDouble() * (MAX - MIN);//随机生成解
            }
        }

        //迭代
        for (int G = 0; G < maxGen; G++) {

            //2.选择优质个体,根据轮盘赌的形式挑选个体
            double sumY = 0;
            for (int i = 0; i < NP; i++) {//计算每个个体的适应度及全部适应度和
                Y[i] = fitness(init_population[i], N);
                sumY += Y[i];
            }
            double addSum = 0;
            for (int i = 0; i < NP; i++) {//记录每个个体别选中的累加和
                addSum += Y[i] / sumY;//当前个体概率
                addSumP[i] = addSum;//累加和
            }
            //根据累加和概率，判断轮盘指针是向那个个体
            for (int i = 0; i < NP; i++) {
                double r = Math.random();
                int index = 0;
                for (int k = 0; k < NP; k++) {
                    if (r < addSumP[k]) {
                        index = k;
                        break;//选择了一个个体下标是index
                    }
                }
                select_population[i] = init_population[index];//选择之后的种群
            }

            //3.交叉
            for (int i = 0; i < NP; i++) {
                double cr = Math.random();
                if (cr < CR) {//交叉
                    //与哪个个体交叉，随机生成一个个体
                    int indiv = random.nextInt(10);
                    //从那个位置交叉，随机生成一个位置
                    int index = random.nextInt(SIZE);
                    for (int j = 0; j < SIZE; j++) {
                        if (j == index) {//发生交叉的片段
                            cr_population[i][j] = select_population[indiv][j];
                        } else { //不发生交叉的片段
                            cr_population[i][j] = select_population[i][j];
                        }
                    }
                } else {//不交叉
                    for (int j = 0; j < SIZE; j++) {
                        cr_population[i][j] = select_population[i][j];
                    }
                }
            }

            //4.变异
            for (int i = 0; i < NP; i++) {
                double mu = Math.random();
                if (mu < MU) {//变异
                    //在那个位置变异
                    int index = random.nextInt(SIZE);
                    for (int j = 0; j < SIZE; j++) {
                        if (j == index) {
                            mu_population[i][j] = mu_population[i][j] - random.nextDouble();
                        } else {
                            mu_population[i][j] = cr_population[i][j];
                        }
                    }
                } else {//不变异
                    for (int j = 0; j < SIZE; j++) {
                        mu_population[i][j] = select_population[i][j];
                    }
                }
            }

            //5.当前最优解
            for (int i = 0; i < NP; i++) {
                best_Y = fitness(mu_population[i], N);
                if (best_Y > fitness(mu_population[i], N)) {
                    best_Y = fitness(mu_population[i], N);
                }
            }
            best[G] = best_Y;
            System.out.println("第" + (G + 1) + "代,最大适应度为:" + best_Y);

            //6.保留优质个体
            for (int i = 0; i < NP; i++) {
                if (fitness(init_population[i], N) > fitness(mu_population[i], N)) {
                    for (int j = 0; j < SIZE; j++) {
                        init_population[i][j] = mu_population[i][j];
                    }
                }
            }
        }//迭代结束

        //7.打印结果
        System.out.println("最优解为：");
        Best(best);

        //结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }

    //打印最优解
    public static void Best(double[] best) {
        double a = 65535;
        int temp = 0;
        for (int i = 0; i < best.length; i++) {
            if (best[i] < a) {
                a = best[i];
                temp = i;
            }
        }
        System.out.println("最优解：" + a + "第一次出现代数: " + (temp + 1));
    }

    //计算适应度，函数式的解
    public static double fitness(double[] individual, int N) {
        double f = 0;
        for (int i = 0; i < N; i++) {
            f += individual[i] * individual[i];
        }
        return f;
    }

}

