package com.shuke.hash.tree;

/**
 *
 * <p>
 * 定义节点接口
 * </p>
 *
 * @author SCA-Liu
 * @version v1.0.0
 * @since 2020-06-09 17:28:26
 * @see com.shuke.red.black.tree
 *
 */
public interface Node {


    /**
     * 插入值，每个节点都需要赋值专属自己的哈希值
     * @param hash 通过key值的高位和低位进行异或生成的hash值，每个元素专属
     * @param key 通过hashCode计算的搜索键值
     * @param val 真正的值
     */
    void insertNode(int hash, int key, int val);


    /**
     * 返回hash值
     * @return
     */
    int getHash();

    /**
     * 判断是树还是链表，是树返回true
     * @return
     */
    boolean isTree();

    /**
     * 打印链表
     */
    void printNodes();


    /**
     * 判断是否含有此节点
     * @return
     */
    boolean containNode(int key);

    /**
     * 返回节点数量
     * @return
     */
    int size();
}
