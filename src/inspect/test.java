package inspect;

import java.awt.List;
import java.io.BufferedWriter;
import java.sql.DatabaseMetaData;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.management.timer.TimerMBean;
import javax.swing.text.TabableView;

import dao.io;

public class test {

	public static boolean isLetterDigitOrChinese(String str) {
		  String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";//������Ҫ��ֱ���޸��������ʽ�ͺ�
		  return str.matches(regex);
		 }
	
	public static void main(String[] args) {
		
		String trace = "G000444003041044104002018040215141602,44011520223000364166,980,0000000000000000,��B5T6H3_9,��B5T6H3_0,null,1,1,3|G001544007003040100902018040218160315,44011520223000364166,1764,0000000000000000,��000000_9,��B5T6H3_0,null,1,1,3|G001544007003040100902018040308054141,44011520223000364166,784,0000000000000000,��000000_9,��B5T6H3_0,null,1,1,3|G001544006002020100702018040417310828,44011520223000364166,3822,0000000000000000,��B5T6H3_0,��B5T6H3_0,null,2,2,3|G001544007003040100902018040808192645,44011520223000364166,1274,0000000000000000,��000000_9,��B5T6H3_0,null,1,2,3|S002844001001040100802018040216483492,44011520223000364166,2254,0000000000000000,��000000_9,��B5T6H3_0,null,1,2,3|G001544007005040103102018040418471689,44011520223000364166,1274,0000000000000000,��B5T6H3_0,��B5T6H3_0,null,2,2,3|S002844001001040100702018033108415493,44011520223000364166,980,0000000000000000,��000000_9,��B5T6H3_0,null,1,1,3|G001544006002020100302018040208373926,44011520223000364166,1764,0000000000000000,��000000_9,��B5T6H3_0,null,1,1,3|G000444003029022900302018040316272512,44011520223000364166,12054,0000000000000000,��000000_9,��B5T6H3_0,null,2,2,3";
				
				
				
		String[] data = trace.split("\\|");
		for(int i=0; i<data.length; i++){
			System.out.println(data[i]);
		}
		
		for(int i=0; i<data.length; i++){
			System.out.println(data[i].substring(21, 35));
		}
	}

}

