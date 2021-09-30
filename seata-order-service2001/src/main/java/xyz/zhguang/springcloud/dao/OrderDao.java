package xyz.zhguang.springcloud.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.zhguang.springcloud.domain.Order;

@Mapper
public interface OrderDao {

    // 新建订单
    void create(Order order);

    //修改订单状态
    void update(@Param("userId")Long userId,@Param("status")Integer status);
}
