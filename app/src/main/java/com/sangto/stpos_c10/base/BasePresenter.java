package com.sangto.stpos_c10.base;

/**
 * MVP   P层
 * 持有view层/model层的引用，最终向view层反馈结果
 */
public interface BasePresenter {
    static final int INSERT = 1;
    static final int DELETE = 2;
    static final int QUERY = 3;
    static final int UPDATE = 4;
    static final int QUERY_RAND = 5;
    static final int QUERY_COUNT = 6; // 查询数据数量
    static final int QUERY_FINISH = 7;
    static final int QUERY_COLLECT = 8;
    static final int QUERY_COLLECT_WRONG = 9;
    static final int QUERY_SECTION = 10;
    static final int QUERY_ENTITY = 11;
    static final int UPDATE_COLLECT = 12;
    static final int UPDATE_COLLECT_CANCEL = 13;
    static final int UPDATE_ANSWER = 14;
    static final int QUERY_WRONG = 15;
    static final int QUERY_COUNT_ONE = 16; // 查询数据数量
    static final int QUERY_COUNT_TWO = 17; // 查询数据数量
    static final int QUERY_COUNT_THREE = 18; // 查询数据数量
    static final int QUERY_COUNT_FOUR = 19; // 查询数据数量
    static final int QUERY_ONE_ONE = 20; // 查询数据数量
    static final int QUERY_ONE_TWO = 21; // 查询数据数量
    static final int QUERY_ONE_THREE = 22; // 查询数据数量
    static final int QUERY_ONE_FOUR = 23; // 查询数据数量
    static final int UPDATE_CLEAR = 24;  // 清除答题记录


    void subcribe();
//    void unSubcribe();


}
