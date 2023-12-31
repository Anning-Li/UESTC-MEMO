package com.starstudio.projectdemo.account.data;

/**
 *  记账“预算”页面中RecyclerView的Adapter所需的数据
 *  用来封装展示本月每一种类的数据
 */
public class BudgetData {
    private String kind;    //预算项分类种类
    private String budget;  //预算项开支预算
    private String balance;     //预算项余额

    public BudgetData(String kind, String budget, String balance) {
        this.kind = kind;
        this.budget = budget;
        this.balance = balance;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
