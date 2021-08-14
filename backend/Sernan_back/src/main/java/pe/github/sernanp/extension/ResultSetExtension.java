package pe.github.sernanp.extension;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class ResultSetExtension {

	public static Boolean hasColumn(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		return exist;
	}

	private static AbstractMap.SimpleEntry<Boolean, Integer> columnIndex(
			ResultSet rs, String columnName) throws SQLException {
		Boolean exist = false;
		int index = -1;
		// for (int start = 0, middle = length / 2, end = length - 1; start <=
		// middle; start++, end--) {
		// if (s.charAt(start) > ' ' || s.charAt(end) > ' ') {
		// return false;
		// }
		// }
		//rs.findColumn(columnLabel)
		if (StringUtils.isNotBlank(columnName) == true) {
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				if (rs.getMetaData().getColumnName(i)
						.equalsIgnoreCase(columnName)) {
					exist = true;
					index = i;
					break;
				}
			}
		}
		return new AbstractMap.SimpleEntry<Boolean, Integer>(exist, index);
	}

	public static Object getValue2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return null;

		return (rs.getObject(index) == null) == false ? rs.getObject(index)
				: null;
	}

	public static Boolean getBoolean2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return false;

		return (rs.getObject(index) == null) == false ? rs.getBoolean(index)
				: false;
	}

	public static Byte getByte2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return null;

		return (rs.getObject(index) == null) == false ? rs.getByte(index)
				: null;
	}

	public static byte[] getBytes2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return null;
		return (rs.getObject(index) == null) == false ? rs.getBytes(index)
				: null;
	}
	
	public static Date getDate2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return null;
		if ((rs.getObject(index) == null) == true)
			return null;
		Date ts = rs.getDate(index);
		return ts;
	}
	public static LocalDate getLocalDate2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return null;
		if ((rs.getObject(index) == null) == true)
			return null;
		return rs.getDate(index).toLocalDate();
//		Date ts = getDate2(rs,columnName);
//		if(ts==null) return null;
//		Instant instant = Instant.ofEpochMilli(ts.getTime());
//		LocalDate ld = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
//		return ld;
	}
	/*public static LocalTime getTime2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return null;
		if ((rs.getObject(index) == null) == true)
			return null;
		Time ts = rs.getTime(index);
		return ts.toLocalTime();
	}*/

	public static BigDecimal getBigDecimal2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return BigDecimal.ZERO;
		return (rs.getObject(index) == null) == false ? rs.getBigDecimal(index)
				: BigDecimal.ZERO;
	}

	public static Double getDouble2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return 0.0;
		return (rs.getObject(index) == null) == false ? rs.getDouble(index)
				: 0.0;
	}
	public static float getFloat2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return 0.0f;
		return (rs.getObject(index) == null) == false ? rs.getFloat(index)
				: 0.0f;
	}
	public static short getShort2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return 0;
		return (rs.getObject(index) == null) == false ? rs.getShort(index)
				: 0;
	}
	public static int getInt2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return 0;
		return (rs.getObject(index) == null) == false ? rs.getInt(index)
				: 0;
	}
	public static long getLong2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return 0;
		return (rs.getObject(index) == null) == false ? rs.getLong(index)
				: 0;
	}
	public static String getString2(ResultSet rs, String columnName)
			throws SQLException {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return StringUtils.EMPTY;
		return (rs.getObject(index) == null) == false ? rs.getString(index)
				: StringUtils.EMPTY;
	}
	public static UUID getGuid2(ResultSet rs, String columnName) throws SQLException
    {
		Boolean exist = false;
		int index = -1;
		AbstractMap.SimpleEntry<Boolean, Integer> column = columnIndex(rs,
				columnName);
		exist = column.getKey();
		index = column.getValue();
		if (exist == false)
			return null;
		return (rs.getObject(index) == null) == false ?UUID.fromString(rs.getString(index))
				: null;
    }
	
}
