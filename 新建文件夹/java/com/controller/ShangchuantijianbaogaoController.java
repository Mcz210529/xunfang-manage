package com.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ShangchuantijianbaogaoEntity;
import com.entity.view.ShangchuantijianbaogaoView;
import com.service.ShangchuantijianbaogaoService;
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
 * 上传体检报告
 * 后端接口
 */
@RestController
@RequestMapping("/shangchuantijianbaogao")
public class ShangchuantijianbaogaoController {
    @Autowired
    private ShangchuantijianbaogaoService shangchuantijianbaogaoService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShangchuantijianbaogaoEntity shangchuantijianbaogao,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date shangchuanshijianstart, 
    		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date shangchuanshijianend,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			shangchuantijianbaogao.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ShangchuantijianbaogaoEntity> ew = new EntityWrapper<ShangchuantijianbaogaoEntity>();
		if(shangchuanshijianstart!=null) ew.ge("shangchuanshijian", shangchuanshijianstart);
        	if(shangchuanshijianend!=null) ew.le("shangchuanshijian", shangchuanshijianend);
		PageUtils page = shangchuantijianbaogaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shangchuantijianbaogao), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShangchuantijianbaogaoEntity shangchuantijianbaogao, HttpServletRequest request){
        EntityWrapper<ShangchuantijianbaogaoEntity> ew = new EntityWrapper<ShangchuantijianbaogaoEntity>();
		PageUtils page = shangchuantijianbaogaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shangchuantijianbaogao), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShangchuantijianbaogaoEntity shangchuantijianbaogao){
       	EntityWrapper<ShangchuantijianbaogaoEntity> ew = new EntityWrapper<ShangchuantijianbaogaoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shangchuantijianbaogao, "shangchuantijianbaogao")); 
        return R.ok().put("data", shangchuantijianbaogaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShangchuantijianbaogaoEntity shangchuantijianbaogao){
        EntityWrapper< ShangchuantijianbaogaoEntity> ew = new EntityWrapper< ShangchuantijianbaogaoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shangchuantijianbaogao, "shangchuantijianbaogao")); 
		ShangchuantijianbaogaoView shangchuantijianbaogaoView =  shangchuantijianbaogaoService.selectView(ew);
		return R.ok("查询上传体检报告成功").put("data", shangchuantijianbaogaoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShangchuantijianbaogaoEntity shangchuantijianbaogao = shangchuantijianbaogaoService.selectById(id);
        return R.ok().put("data", shangchuantijianbaogao);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShangchuantijianbaogaoEntity shangchuantijianbaogao = shangchuantijianbaogaoService.selectById(id);
        return R.ok().put("data", shangchuantijianbaogao);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShangchuantijianbaogaoEntity shangchuantijianbaogao, HttpServletRequest request){
    	shangchuantijianbaogao.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shangchuantijianbaogao);
        shangchuantijianbaogaoService.insert(shangchuantijianbaogao);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShangchuantijianbaogaoEntity shangchuantijianbaogao, HttpServletRequest request){
    	shangchuantijianbaogao.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(shangchuantijianbaogao);
        shangchuantijianbaogaoService.insert(shangchuantijianbaogao);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShangchuantijianbaogaoEntity shangchuantijianbaogao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shangchuantijianbaogao);
        shangchuantijianbaogaoService.updateById(shangchuantijianbaogao);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        shangchuantijianbaogaoService.deleteBatchIds(Arrays.asList(ids));
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
		
		Wrapper<ShangchuantijianbaogaoEntity> wrapper = new EntityWrapper<ShangchuantijianbaogaoEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			wrapper.eq("zhanghao", (String)request.getSession().getAttribute("username"));
		}

		int count = shangchuantijianbaogaoService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
