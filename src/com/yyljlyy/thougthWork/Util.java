package com.yyljlyy.thougthWork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ext.LexicalHandler;

/**
 * 解析工具组
 * 
 * @author lee
 * 
 */
public class Util {
	String[] kind = { "pills", "book", "chocolates", "chocolate","bar"};

	/**
	 * 
	 * @param path
	 * @return
	 */
	public BufferedReader getBufferedReader(String path) {
		File file = new File(path);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return br;
	}

	/**
	 * 获取单行信息
	 * 
	 * @param br
	 * @return
	 */
	public List<String> getLines(BufferedReader br) {
		List<String> lines = new ArrayList<String>();
		// 解析整个数据流
		String line;
		try {
			while (null != (line = br.readLine())) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * 将获得的每一行转换为product集合
	 * 
	 * @param lines
	 * @return
	 */

	public List<Input> getProduct(List<String> lines) {
		List<Input> inputs = new ArrayList<Input>();
		Good pro = null;
		Input input = new Input();
		for (String str : lines) {
			if (str.matches("[iI][nN][pP][uU][tT] \\d+:")) {
				if (null != input) {
					inputs.add(input);
				}
				input = new Input();
			} else {
				pro = getProduct(str);
				input.goods.add(pro);
				input.totalTax += pro.getTax();
				input.totalPrice += pro.getTotalPrice();

			}
		}
		inputs.add(input);
		return inputs;
	}

	/**
	 * 逐行解析为Goods
	 * 
	 * @param str
	 * @return
	 */
	public Good getProduct(String str) {
		String strs[] = str.split("[ ]");
		Good good = new Good();
		boolean isFormatRight = true;
		if (strs.length < 4) {
			throw new RuntimeException("the input is too low");
		}
		do {
			int length = strs.length;
			if (strs[0].matches("\\d+")) {
				good.setNum(Integer.parseInt(strs[0]));

			} else {
				isFormatRight = false;
			}
			if (strs[1].equalsIgnoreCase("imported")) {
				good.setImported(true);
			}
			if (strs.length > 3) {
				String name = strs[length - 3];
				for (int i = 0; i < kind.length; i++) {
					if (kind[i].equalsIgnoreCase(name)) {
						//System.out.println("分解" + name);
						//System.out.println("数组" + kind[i]);
						good.setTaxAccept(false);
					}
				}

			}
			if (strs[length - 1].matches("\\d+.\\d+")) {
				good.setPrice(Float.parseFloat(strs[length - 1]));
			} else {
				isFormatRight = false;
			}
			good.setName(strs[length - 3]);
		} while (false);
		if (!isFormatRight) {
			throw new RuntimeException("the format is wrong");
		}
		good.setContent(str.replaceAll("at[ ]*\\d*[ .]\\d*", ""));
		return good;
	}

	/**
	 * 输出函数
	 * 
	 * @param path
	 */
	public void output(String path) {
		int i = 1;
		List<Input> inputs = getProduct(getLines(getBufferedReader(path)));
		for (Input input : inputs) {
			System.out.println("OUTPUT " + i++ + ":");
			for (Good good : input.goods) {
				System.out.println(good.getContent() + ":"
						+ String.format("%.2f", good.getPrice()));
			}
			System.out.println("Sales Taxes:"
					+ String.format("%.2f", input.totalTax));
			System.out.println("Total:"
					+ String.format("%.2f", input.totalPrice));
		}
	}
}
