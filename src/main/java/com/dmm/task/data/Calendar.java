package com.dmm.task.data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
	public static void main(String[] args) {

		LocalDate d = LocalDate.of(2022, 11, 01);
		LocalDate d2 = d.plusDays(d.lengthOfMonth());

		List<List<LocalDate>> lists = new ArrayList<>();
		List<LocalDate> listDate = new ArrayList<>();

		DayOfWeek w = d.getDayOfWeek();
		DayOfWeek w3 = d2.getDayOfWeek();

		LocalDate ds = d.minusDays(7 - (7 - w.getValue()));
		int dss = ds.lengthOfMonth() - ds.getDayOfMonth() + d.lengthOfMonth() + (7 - w3.getValue() + 1);

		for (int j = 0; j < dss; j++) {
			DayOfWeek w2 = ds.getDayOfWeek();

			//			String str = w2.getDisplayName(TextStyle.NARROW, Locale.getDefault());
			//			System.out.print(ds.getDayOfMonth() + "( " + str + " )");
			listDate.add(ds);
			ds = ds.plusDays(1);
			if (ds.getDayOfWeek() == DayOfWeek.SUNDAY) {
				System.out.println();
				lists.add(listDate);
				listDate = new ArrayList<>();
			}
		}
		System.out.println(".......");
		for (List<LocalDate> lists1 : lists) {
			System.out.println(lists1);
		}
	}
}