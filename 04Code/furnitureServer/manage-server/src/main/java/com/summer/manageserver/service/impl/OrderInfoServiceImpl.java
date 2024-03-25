package com.summer.manageserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.summer.commonmodule.entity.dto.OrderStateDTO;
import com.summer.commonmodule.entity.dto.OrderUserDTO;
import com.summer.commonmodule.entity.dto.ShopSalesDTO;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.mapper.OrderInfoMapper;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.manageserver.entity.bo.OrderInfoBO;
import com.summer.manageserver.entity.vo.OrderInfoVO;
import com.summer.manageserver.entity.vo.OrderStateUpdateVO;
import com.summer.manageserver.entity.vo.OrderStateVO;
import com.summer.manageserver.entity.vo.ShopSalesVO;
import com.summer.manageserver.service.OrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 订单信息
 * @author WangLang
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    @Override
    public OrderStateVO getOrderState() {
        OrderStateVO orderStateVO = new OrderStateVO();
        orderStateVO.setTotal(0);

        List<OrderStateDTO> orderStateDTOS = orderInfoMapper.getOrderState();
        for (OrderStateDTO dto : orderStateDTOS) {
            switch (dto.getState()) {
                case 1:
                    orderStateVO.setOrdered(dto.getCount());
                    orderStateVO.setTotal(orderStateVO.getTotal() + dto.getCount());
                    break;
                case 2:
                    orderStateVO.setDetermined(dto.getCount());
                    orderStateVO.setTotal(orderStateVO.getTotal() + dto.getCount());
                    break;
                case 3:
                    orderStateVO.setCompleted(dto.getCount());
                    orderStateVO.setTotal(orderStateVO.getTotal() + dto.getCount());
                    break;
                case -1:
                    orderStateVO.setAfterSales(dto.getCount());
                    orderStateVO.setTotal(orderStateVO.getTotal() + dto.getCount());
                    break;
                default:
                    orderStateVO.setTotal(orderStateVO.getTotal() + dto.getCount());
            }
        }

        return orderStateVO;
    }

    @Override
    public List<ShopSalesVO> getShopSales() {
        List<ShopSalesVO> shopSalesVOS = new ArrayList<>();

        List<ShopSalesDTO> shopSalesDTOS = orderInfoMapper.queryShopSales();
        for (ShopSalesDTO dto : shopSalesDTOS) {
            ShopSalesVO shopSalesVO = new ShopSalesVO();
            shopSalesVO.setName(dto.getMonth())
                    .setValue(dto.getTotal());
            shopSalesVOS.add(shopSalesVO);
        }

        // 排序
        shopSalesVOS.sort(Comparator.comparing(ShopSalesVO::getName));

        return shopSalesVOS;
    }

    @Override
    public OrderInfoVO getOrderInfo(String field, String value, Integer page) {
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        List<OrderInfoBO> orderInfoBOS = new ArrayList<>();

        // 计数
        long total = orderInfoMapper.countOrder();

        // 当filed为空即查询全部
        List<OrderUserDTO> orderUserDTOS = orderInfoMapper.queryOrderUser(field, value, 10 * (page - 1), 10);
        for (OrderUserDTO dto : orderUserDTOS) {
            OrderInfoBO orderInfoBO = new OrderInfoBO();
            BeanUtil.copyProperties(dto, orderInfoBO);
            orderInfoBO.setNickname(Objects.isNull(dto.getCustomer()) ? "-1" : dto.getCustomer().getNickname())
                    .setPhone(Objects.isNull(dto.getCustomer()) ? "-1" : dto.getCustomer().getPhone());
            orderInfoBOS.add(orderInfoBO);
        }

        orderInfoVO.setOrderInfoBOS(orderInfoBOS)
                .setPage(page)
                .setTotal((int) total);

        return orderInfoVO;
    }

    @Override
    public void updateOrderState(OrderStateUpdateVO orderStateUpdateVO) {
        if (Objects.isNull(orderStateUpdateVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        String[] orderIds = orderStateUpdateVO.getOrderIds().toString().substring(1, orderStateUpdateVO.getOrderIds().toString().length() - 1).split(StrUtil.COMMA + StrUtil.SPACE);
        long updateCount = orderInfoMapper.updateOrderState(orderIds, orderStateUpdateVO.getState());
        if ((int) updateCount == 0) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "修改失败", logger);
        }

    }
}
