package assn02;

import java.util.Hashtable;
import java.util.Scanner;

public class JavaWarmUp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int final_item_count = Integer.parseInt(sc.nextLine());

        String[] date = new String[final_item_count];
        String[] assembly_time = new String[final_item_count];
        String[] category = new String[final_item_count];
        int[] quantity = new int[final_item_count];
        float[] fee = new float[final_item_count];
        float[] time = new float[final_item_count];
        int[] cost = new int[final_item_count];

        for (int i = 0; i < final_item_count; i++) {

            String[] data = sc.nextLine().split(" ");
            date[i] = data[0];
            assembly_time[i] = data[1];
            category[i] = data[2];
            fee[i] = Float.parseFloat(data[3]);
            quantity[i] = Integer.parseInt(data[4]);
            time[i] = Float.parseFloat(data[5]);
            cost[i] = Integer.parseInt(data[6]);
        }

        int maxIndex = getMaxPriceIndex(fee);

        System.out.println("Highest per unit assembling fee:");
        System.out.println("\tWhen: " + date[maxIndex] + " " + assembly_time[maxIndex]);
        System.out.println("\tCategory: " + category[maxIndex]);
        System.out.println("\tPrice: " + fee[maxIndex]);

        int lowIndex = getLowPriceIndex(fee);
        System.out.println("Lowest per unit assembling fee:");
        System.out.println("\tWhen: " + date[lowIndex] + " " + assembly_time[lowIndex]);
        System.out.println("\tCategory: " + category[lowIndex]);
        System.out.println("\tPrice: " + fee[lowIndex]);

        int[] CategoryCounts = TotalByCategory(category, quantity, final_item_count);
        double[] avgAssemblyFeeByCategory = averageAssemblingFeeByCategory(category, quantity, fee, final_item_count);
        double[] avgNetProfit = averageNetProfit(category, fee, quantity, cost, time, final_item_count);
        float fee_a = (float) avgAssemblyFeeByCategory[0];
        float fee_b = (float) avgAssemblyFeeByCategory[1];
        float fee_c = (float) avgAssemblyFeeByCategory[2];

        float profit_a = (float) avgNetProfit[0];
        float profit_b = (float) avgNetProfit[1];
        float profit_c = (float) avgNetProfit[2];

        //Phone Statistics
        System.out.println("Statistic of phone");
        System.out.println("\tQuantity: " + CategoryCounts[0]);
        System.out.printf("\tAverage Assembling fee: " + "%.2f", fee_a);
        System.out.println();
        System.out.printf("\tAverage Net Profit: " + "%.2f", profit_a);
        System.out.println();

        //Laptop Statistics
        System.out.println("Statistic of laptop");
        System.out.println("\tQuantity: " + CategoryCounts[1]);
        System.out.printf("\tAverage Assembling fee: " + "%.2f", fee_b);
        System.out.println();
        System.out.printf("\tAverage Net Profit: " + "%.2f", profit_b);
        System.out.println();

        //Smartwatch Statistics
        System.out.println("Statistic of smart_watch");
        System.out.println("\tQuantity: " + CategoryCounts[2]);
        System.out.printf("\tAverage Assembling fee: " + "%.2f", fee_c);
        System.out.println();
        System.out.printf("\tAverage Net Profit: " + "%.2f", profit_c);
        System.out.println();
    }
    private static int getMaxPriceIndex(float[] fee) {
        float max = fee[0];
        int maxIndex = 0;
        for (int i = 1; i < fee.length; i++) {
            if (fee[i] > max) {
                max = fee[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    private static int getLowPriceIndex(float[] fee) {
        float low = fee[0];
        int lowIndex = 0;
        for (int i = 1; i < fee.length; i++) {
            if (fee[i] < low) {
                low = fee[i];
                lowIndex = i;
            }
        }
        return lowIndex;
    }
    private static int[] TotalByCategory(String[] Categories, int[] quantity, int final_item_count) {
        Hashtable<String, Integer> categories = new Hashtable<String, Integer>();
        categories.put("phone", 0);
        categories.put("laptop", 0);
        categories.put("smart_watch", 0);
        if (Categories.length == quantity.length) {
            for (int i = 0; i < final_item_count; i++) {
                if (categories.containsKey(Categories[i])) {
                    categories.put(Categories[i], categories.get(Categories[i]) + quantity[i]);
                }
            }
        }
        int[] CategoryCounts = new int[3];
        CategoryCounts[0] = categories.get("phone");
        CategoryCounts[1] = categories.get("laptop");
        CategoryCounts[2] = categories.get("smart_watch");
        return CategoryCounts;
        
    }
    private static double[] averageAssemblingFeeByCategory(String[] Categories, int[] quantity, float[] fee, int final_item_count) {
        Hashtable<String, Integer> categories = new Hashtable<String, Integer>();
        categories.put("phone", 0);
        categories.put("laptop", 0);
        categories.put("smart_watch", 0);
        Hashtable<String, Float> categoryFee = new Hashtable<String, Float>();
        categoryFee.put("phone", 0f);
        categoryFee.put("laptop", 0f);
        categoryFee.put("smart_watch", 0f);
        if (Categories.length == quantity.length) {
            for (int i = 0; i < final_item_count; i++) {
                if (categories.containsKey(Categories[i])) {
                    categories.put(Categories[i], categories.get(Categories[i]) + quantity[i]);
                    categoryFee.put(Categories[i], categoryFee.get(Categories[i]) + (quantity[i] * fee[i]));
                }
            }
        }
        double[] avgAssemblyFeeByCategory = new double[3];
        avgAssemblyFeeByCategory[0] = categoryFee.get("phone") / categories.get("phone");
        avgAssemblyFeeByCategory[1] = categoryFee.get("laptop") / categories.get("laptop");
        avgAssemblyFeeByCategory[2] = categoryFee.get("smart_watch") / categories.get("smart_watch");
        return avgAssemblyFeeByCategory;
    }
    private static double[] averageNetProfit(String[] category, float[] fee, int[] quantity, int[] cost, float[] time, int final_item_count) {
        //categoryProfit = (fee * quantity) - (cost + (16 * time);
        Hashtable<String, Integer> categories = new Hashtable<String, Integer>();
        categories.put("phone", 0);
        categories.put("laptop", 0);
        categories.put("smart_watch", 0);
        Hashtable<String, Float> categoryProfit = new Hashtable<String, Float>();
        categoryProfit.put("phone", 0f);
        categoryProfit.put("laptop", 0f);
        categoryProfit.put("smart_watch", 0f);
        if (category.length == quantity.length) {
            for (int i = 0; i < final_item_count; i++) {
                if (categories.containsKey(category[i])) {
                    categories.put(category[i], categories.get(category[i]) + quantity[i]);
                    categoryProfit.put(category[i], categoryProfit.get(category[i]) + ((fee[i] * quantity[i]) - (cost[i] + (16 * time[i]))));
                }
            }
        }
        double[] avgNetProfit = new double[3];
        avgNetProfit[0] = categoryProfit.get("phone") / categories.get("phone");
        avgNetProfit[1] = categoryProfit.get("laptop") / categories.get("laptop");
        avgNetProfit[2] = categoryProfit.get("smart_watch") / categories.get("smart_watch");
        return avgNetProfit;
    }
}
