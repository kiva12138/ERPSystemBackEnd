package com.sun.erpbackend.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态数据类
 * @author 夜流歌
 *
 */
public class ERPStaticData {
	
	public static List<String> materialClass = new ArrayList<String>();
	public static List<String> materialStatus = new ArrayList<String>();	
	public static List<String> billStatusList = new ArrayList<String>();
	public static List<String> stationStatusList = new ArrayList<String>();
	public static List<String> treeStatusList = new ArrayList<String>();
	public static List<String> refuseReasonList = new ArrayList<>();
	
	public static int defaultMaterialWarnThreshold = 50;
	public static int defaultMaterialDangerThreshold = 20;
	
	public static int materialCategoryPagination = 15;
	public static int billPagination = 15;
	public static int stationPagination = 15;
	public static int recordPagination = 15;
	public static int stationStatisticsPagination = 35;
	public static int treePagination = 30;

	
	public ERPStaticData () {
		materialClass.add(1, "生产原料");
		materialClass.add(2, "过程物料");
		materialClass.add(3, "最终产品");

		materialStatus.add(1, "存货充足");
		materialStatus.add(2, "缺货");
		materialStatus.add(3, "存货偏少");
		
		billStatusList.add(1, "待分配");
		billStatusList.add(2, "待接收");
		billStatusList.add(3, "工位拒收");
		billStatusList.add(4, "生产中");
		billStatusList.add(5, "生产超时");
		billStatusList.add(6, "停滞中");
		billStatusList.add(7, "已完成");
		billStatusList.add(8, "已停止");
		
		stationStatusList.add(1, "生产中");
		stationStatusList.add(2, "已停工");
		stationStatusList.add(3, "休整中");
		
		treeStatusList.add(1, "正常");
		treeStatusList.add(2, "谨慎");
		treeStatusList.add(3, "禁用");
		
		refuseReasonList.add(1, "物料不足");
		refuseReasonList.add(2, "正在忙于其他生产");
		refuseReasonList.add(3, "工位维护");
		refuseReasonList.add(4, "其他原因");
	}
}
