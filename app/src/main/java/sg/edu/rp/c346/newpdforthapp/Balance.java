package sg.edu.rp.c346.newpdforthapp;

import java.util.ArrayList;

public class Balance {
    private ArrayList<Float> incomeList;
    private ArrayList<Float> spendList;
    private ArrayList<String> dateListIncome;
    private ArrayList<String> dateListSpend;

    public Balance(ArrayList<Float> incomeList, ArrayList<Float> spendList, ArrayList<String> dateListIncome, ArrayList<String> dateListSpend) {
        this.incomeList = incomeList;
        this.spendList = spendList;
        this.dateListIncome = dateListIncome;
        this.dateListSpend = dateListSpend;
    }

    public ArrayList<Float> getIncomeList() {
        return incomeList;
    }

    public ArrayList<Float> getSpendList() {
        return spendList;
    }

    public ArrayList<String> getDateListIncome() {
        return dateListIncome;
    }

    public ArrayList<String> getDateListSpend() {
        return dateListSpend;
    }
}
