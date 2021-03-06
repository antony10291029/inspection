package divideByReleaser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;

import escapeAuditByType.MatchUserInfo;

public class DividedByCardId {
	
	private static String inPath = "I:/稽查/exVehTypeMulRes.csv";
	private static String outPath = "I:/稽查/根据卡号划分省份/";
	private static String userInfoPath = "I:/稽查/userInfo.csv";
	
	public static void main(String[] args) {
		splitProByCardId(userInfoPath, inPath, outPath);
	}
	
	public static String getCardIdRealeaser(String ProvinceCode) {
		switch (ProvinceCode) {
		case "05":
			return "军车";
		case "11":
			return "北京市";
		case "12":
			return "天津市";
		case "31":
			return "上海市";
		case "50":
			return "重庆市";
		case "64":
			return "宁夏回族自治区";
		case "65":
			return "新疆维吾尔自治区";
		case "15":
			return "内蒙古自治区";
		case "45":
			return "广西壮族自治区";
		case "13":
			return "河北省";
		case "14":
			return "山西省";
		case "21":
			return "辽宁省";
		case "22":
			return "吉林省";
		case "23":
			return "黑龙江省";
		case "32":
			return "江苏省";
		case "33":
			return "浙江省";
		case "34":
			return "安徽省";
		case "35":
			return "福建省";
		case "36":
			return "江西省";
		case "37":
			return "山东省";
		case "41":
			return "河南省";
		case "42":
			return "湖北省";
		case "43":
			return "湖南省";
		case "44":
			return "广东省";
		case "51":
			return "四川省";
		case "52":
			return "贵州省";
		case "53":
			return "云南省";
		case "61":
			return "陕西省";
		case "62":
			return "甘肃省";
		case "63":
			return "青海省";
		default:
			return "";
		}
	}
	
	/**
	 * 对筛选出来的存在多种出口车型的结果文件进行省份划分和匹配用户数据处理
	 */
	public static void splitProByCardId(String userInfoPath, String inPath, String outPath) {
		Map<String, ArrayList<String>> ProvinceMap = new HashMap<>();
		Map<String, ArrayList<String>> userInfoMap = MatchUserInfo.matchUserInfo(userInfoPath);

		// 读文件
		try {
			InputStreamReader inStream = new InputStreamReader(new FileInputStream(inPath), "UTF-8");
			BufferedReader reader = new BufferedReader(inStream);

			String line = "";
			String[] lineArray;
			while ((line = reader.readLine()) != null) {
				lineArray = line.split("\\|");

				String[] data  = lineArray[0].split(",");
				String cardId  = data[1];// 用户卡编号
				String provinceCode = cardId.substring(0, 2);
				ArrayList<String> userInfo;
				
				if(userInfoMap.containsKey(cardId)){
					userInfo = userInfoMap.get(cardId);
				}else{
					continue;
				}
				
				//System.out.println(provinceCode);
				String province = getCardIdRealeaser(provinceCode);
				
				if (province.isEmpty()) {
					System.out.println("出现异常省份！！");
					continue;
				}
				
				//对长数字串进行处理，使其可以在Excel中显示
				String newLine = adaptExcel(line);
				//String newLine = line;
				if (ProvinceMap.containsKey(province)) {
					ArrayList<String> listTrace = ProvinceMap.get(province);
					int count = Integer.parseInt(listTrace.get(0));
					count++;
					listTrace.set(0, count+"");
					String info = "," + userInfo.get(0) + "," + userInfo.get(1) + "|";
					listTrace.add(newLine.replaceAll("\\|", info) + "," + userInfo.get(0) + "," + userInfo.get(1));
					ProvinceMap.put(province, listTrace);
				} else {
					ArrayList<String> listTrace = new ArrayList<>();
					listTrace.add("1");
					String info = "," + userInfo.get(0) + "," + userInfo.get(1) + "|";
					listTrace.add(newLine.replaceAll("\\|", info) + "," + userInfo.get(0) + "," + userInfo.get(1));
					ProvinceMap.put(province, listTrace);
				}				
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(inPath + " read finish!");

		// 写数据
		writeData(outPath, ProvinceMap);
		
		System.out.println("*************分省结束*************");
	}

	public static void writeData(String outPath, Map<String, ArrayList<String>> dataMap) {
		// 写文件
		System.out.println(outPath + "  writing !");
		int sum = 0;
		try {		
			for (String province : dataMap.keySet()) {
				
				ArrayList<String> listTrace = dataMap.get(province);
				String path = outPath + province + "_" + listTrace.get(0) + ".csv";
				OutputStreamWriter writerStream = new OutputStreamWriter(new FileOutputStream(path), "utf-8");
				BufferedWriter writer = new BufferedWriter(writerStream);
				
				writer.write("交易ID,ETC ID,交易金额,OBU ID,"
						+ "入口实际收费车牌号,出口实际收费车牌号,出口识别车牌号,入口收费车型,出口收费车型,支付类型,发行车牌（国家中心）,发行车型（国家中心）\n");
			
				for (int j = 1; j < listTrace.size(); j++) {
					String[] data = listTrace.get(j).split("\\|");
					for(int i = 0; i < data.length; i++) {
						writer.write(data[i]);
						writer.write("\n");
					}
					sum++;
				}
			
				writer.flush();
				writer.close();

				System.out.println(path + " write end.");
			}
			System.out.println("共包括" + sum + "条记录");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String adaptExcel(String line) {
		String[] data = line.split("\\|");
		String newLine = "";
		
		for(int i = 0; i < data.length; i++) {
			String record[] = data[i].split(",");
			record[1] = "'" + record[1];//用户卡ID
			if(!record[3].equals("null")) {
				record[3] = "'" + record[3];
			}
			
			for(int j = 0; j < record.length; j++) {
				if(j == record.length-1) {
					newLine += record[j];
					break;
				}
				newLine += record[j] + ",";
			}
			
			if(i < data.length-1) {
				newLine += "|";
			}
		}
		
		//System.out.println(newLine);
		return newLine;
		
	}

}
