package com.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.TijianleixingEntity;
import com.entity.view.TijianleixingView;
import com.service.TijianleixingService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * 体检类型
 * 后端接口

 */
@RestController
@RequestMapping("/tijianleixing")
public class TijianleixingController {
    @Autowired
    private TijianleixingService tijianleixingService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TijianleixingEntity tijianleixing,
		HttpServletRequest request){
        EntityWrapper<TijianleixingEntity> ew = new EntityWrapper<TijianleixingEntity>();
		PageUtils page = tijianleixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tijianleixing), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,TijianleixingEntity tijianleixing, HttpServletRequest request){
        EntityWrapper<TijianleixingEntity> ew = new EntityWrapper<TijianleixingEntity>();
		PageUtils page = tijianleixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tijianleixing), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( TijianleixingEntity tijianleixing){
       	EntityWrapper<TijianleixingEntity> ew = new EntityWrapper<TijianleixingEntity>();
      	ew.allEq(MPUtil.allEQMapPre( tijianleixing, "tijianleixing")); 
        return R.ok().put("data", tijianleixingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(TijianleixingEntity tijianleixing){
        EntityWrapper< TijianleixingEntity> ew = new EntityWrapper< TijianleixingEntity>();
 		ew.allEq(MPUtil.allEQMapPre( tijianleixing, "tijianleixing")); 
		TijianleixingView tijianleixingView =  tijianleixingService.selectView(ew);
		return R.ok("查询体检类型成功").put("data", tijianleixingView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TijianleixingEntity tijianleixing = tijianleixingService.selectById(id);
        return R.ok().put("data", tijianleixing);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TijianleixingEntity tijianleixing = tijianleixingService.selectById(id);
        return R.ok().put("data", tijianleixing);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TijianleixingEntity tijianleixing, HttpServletRequest request){
    	tijianleixing.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(tijianleixing);
        tijianleixingService.insert(tijianleixing);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody TijianleixingEntity tijianleixing, HttpServletRequest request){
    	tijianleixing.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(tijianleixing);
        tijianleixingService.insert(tijianleixing);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TijianleixingEntity tijianleixing, HttpServletRequest request){
        //ValidatorUtils.validateEntity(tijianleixing);
        tijianleixingService.updateById(tijianleixing);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        tijianleixingService.deleteBatchIds(Arrays.asList(ids));
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
		
		Wrapper<TijianleixingEntity> wrapper = new EntityWrapper<TijianleixingEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = tijianleixingService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
