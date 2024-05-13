package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.TiyantaocanEntity;
import com.entity.view.TiyantaocanView;
import com.entity.vo.TiyantaocanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 体检套餐

 */
public interface TiyantaocanDao extends BaseMapper<TiyantaocanEntity> {
	
	List<TiyantaocanVO> selectListVO(@Param("ew") Wrapper<TiyantaocanEntity> wrapper);
	
	TiyantaocanVO selectVO(@Param("ew") Wrapper<TiyantaocanEntity> wrapper);
	
	List<TiyantaocanView> selectListView(@Param("ew") Wrapper<TiyantaocanEntity> wrapper);

	List<TiyantaocanView> selectListView(Pagination page,@Param("ew") Wrapper<TiyantaocanEntity> wrapper);
	
	TiyantaocanView selectView(@Param("ew") Wrapper<TiyantaocanEntity> wrapper);
	
}
