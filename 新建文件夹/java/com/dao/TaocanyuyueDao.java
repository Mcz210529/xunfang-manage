package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.TaocanyuyueEntity;
import com.entity.view.TaocanyuyueView;
import com.entity.vo.TaocanyuyueVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 套餐预约

 */
public interface TaocanyuyueDao extends BaseMapper<TaocanyuyueEntity> {
	
	List<TaocanyuyueVO> selectListVO(@Param("ew") Wrapper<TaocanyuyueEntity> wrapper);
	
	TaocanyuyueVO selectVO(@Param("ew") Wrapper<TaocanyuyueEntity> wrapper);
	
	List<TaocanyuyueView> selectListView(@Param("ew") Wrapper<TaocanyuyueEntity> wrapper);

	List<TaocanyuyueView> selectListView(Pagination page,@Param("ew") Wrapper<TaocanyuyueEntity> wrapper);
	
	TaocanyuyueView selectView(@Param("ew") Wrapper<TaocanyuyueEntity> wrapper);
	
}
