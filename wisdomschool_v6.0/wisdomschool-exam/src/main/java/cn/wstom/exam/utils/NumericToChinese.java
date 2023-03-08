package cn.wstom.exam.utils;

public class NumericToChinese
{

    public static String getChinseNum(int num){

        String[] units = new String[] {"十", "百", "千", "万", "十", "百", "千", "亿"};


        String[] numeric = new String[] {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

        String temp = String.valueOf(num);
        String res = "";

        if (null != temp)
        {
            for (int k = -1; temp.length() > 0; k++)
            {
                int j = Integer.parseInt(temp.substring(temp.length() - 1, temp.length()));
                String rtemp = numeric[j];

                if (j != 0 && k != -1 || k % 8 == 3 || k % 8 == 7)
                {
                    rtemp += units[k % 8];
                }

                res = rtemp + res;

                temp = temp.substring(0, temp.length() - 1);
            }

            while (res.endsWith(numeric[0]))
            {
                res = res.substring(0, res.lastIndexOf(numeric[0]));
            }

            while (res.indexOf(numeric[0] + numeric[0]) != -1)
            {
                res = res.replaceAll(numeric[0] + numeric[0], numeric[0]);
            }

            for (int m = 1; m < units.length; m++)
            {
                res = res.replaceAll(numeric[0] + units[m], units[m]);
                if(res.equals("一十一")){
                	res="十一";
                }
                else if(res.startsWith("一十")){
                	res=res.replaceAll("一","");
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
    	String str= NumericToChinese.getChinseNum(0);
    	System.out.println(str);
	}

}
