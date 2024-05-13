package com.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.TiyantaocanEntity;
import com.entity.view.TiyantaocanView;
import com.service.TiyantaocanService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * 体检套餐
 * 后端接口

 */
@RestController
@RequestMapping("/tiyantaocan")
public class TiyantaocanController {
    @Autowired
    private TiyantaocanService tiyantaocanService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TiyantaocanEntity tiyantaocan,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date shangjiashijianstart, 
    		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date shangjiashijianend,
		HttpServletRequest request){
        EntityWrapper<TiyantaocanEntity> ew = new EntityWrapper<TiyantaocanEntity>();
		if(shangjiashijianstart!=null) ew.ge("shangjiashijian", shangjiashijianstart);
        	if(shangjiashijianend!=null) ew.le("shangjiashijian", shangjiashijianend);
		PageUtils page = tiyantaocanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tiyantaocan), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,TiyantaocanEntity tiyantaocan, HttpServletRequest request){
        EntityWrapper<TiyantaocanEntity> ew = new EntityWrapper<TiyantaocanEntity>();
		PageUtils page = tiyantaocanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tiyantaocan), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( TiyantaocanEntity tiyantaocan){
       	EntityWrapper<TiyantaocanEntity> ew = new EntityWrapper<TiyantaocanEntity>();
      	ew.allEq(MPUtil.allEQMapPre( tiyantaocan, "tiyantaocan")); 
        return R.ok().put("data", tiyantaocanService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(TiyantaocanEntity tiyantaocan){
        EntityWrapper< TiyantaocanEntity> ew = new EntityWrapper< TiyantaocanEntity>();
 		ew.allEq(MPUtil.allEQMapPre( tiyantaocan, "tiyantaocan")); 
		TiyantaocanView tiyantaocanView =  tiyantaocanService.selectView(ew);
		return R.ok("查询体检套餐成功").put("data", tiyantaocanView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TiyantaocanEntity tiyantaocan = tiyantaocanService.selectById(id);
        return R.ok().put("data", tiyantaocan);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TiyantaocanEntity tiyantaocan = tiyantaocanService.selectById(id);
        return R.ok().put("data", tiyantaocan);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TiyantaocanEntity tiyantaocan, HttpServletRequest request){
    	tiyantaocan.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(tiyantaocan);
        tiyantaocanService.insert(tiyantaocan);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody TiyantaocanEntity tiyantaocan, HttpServletRequest request){
    	tiyantaocan.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(tiyantaocan);
        tiyantaocanService.insert(tiyantaocan);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TiyantaocanEntity tiyantaocan, HttpServletRequest request){
        //ValidatorUtils.validateEntity(tiyantaocan);
        tiyantaocanService.updateById(tiyantaocan);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        tiyantaocanService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<TiyantaocanEntity> wrapper = new EntityWrapper<TiyantaocanEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = tiyantaocanService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
