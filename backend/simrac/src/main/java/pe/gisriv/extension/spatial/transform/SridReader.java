package pe.gisriv.extension.spatial.transform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SridReader {
	private static List<SpatialReferenceSystem> _items;
	static {
		try {
			_items = getItems();
		} catch (IOException e) {
			_items = null;
		}
	}

	static List<SpatialReferenceSystem> getItems() throws IOException {
		List<SpatialReferenceSystem> items = new ArrayList<SpatialReferenceSystem>();
		InputStream input = SridReader.class.getResourceAsStream("/PROJ4.tsv");
		BufferedReader br = new BufferedReader(new InputStreamReader(input,
				"UTF-8"));
		br.mark(1);
		if (br.read() != 0xFEFF)
			br.reset();
		String line = StringUtils.EMPTY;
		while ((line = br.readLine()) != null) {
			String[] parts = line.split("\t");
			Integer srid = Integer.parseInt(parts[0]);
			String auth = parts[1];
			Integer asrid = Integer.valueOf(parts[2]);
			String name = parts[3];
			String proj4 = parts[4];
			String srText = parts.length > 5 ? parts[5] : "";
			String srTextEsri = parts.length > 6 ? parts[6] : "";
			items.add(new SridReader().new SpatialReferenceSystem(srid, auth,
					asrid, name, proj4, srText, srTextEsri));
		}
		br.close();
		return items;
	}

	public static SpatialReferenceSystem getById(Integer srid)
			throws IOException {
		List<SpatialReferenceSystem> items = _items;// getItems();
		SpatialReferenceSystem srs = null;
		for (SpatialReferenceSystem item : items) {
			srs = item.equalsById(srid);
			if (srs != null)
				break;
		}
		return srs;
	}

	public static String getEsriNameById(Integer srid) throws IOException {
		SpatialReferenceSystem srs = getById(srid);
		return srs == null ? "" : srs.getSrTextEsri();
	}

	public static String getEpsgNameById(Integer srid) throws IOException {
		SpatialReferenceSystem srs = getById(srid);
		return srs == null ? "" : srs.getSrText();
	}

	public static SpatialReferenceSystem getByEsriName(String esriName)
			throws IOException {
		List<SpatialReferenceSystem> items = _items;
		SpatialReferenceSystem srs = null;
		for (SpatialReferenceSystem item : items) {
			srs = item.equalsByEsriName(esriName);
			if (srs != null)
				break;
		}
		return srs;
	}

	public static int getIdByEsriName(String esriName) throws IOException {
		SpatialReferenceSystem srs = getByEsriName(esriName);
		return (srs == null) ? 0 : srs.getSrid();
	}

	public class SpatialReferenceSystem {
		private Integer _srid;
		private String _authorityName;
		private Integer _authoritySrid;
		private String _refSysName;
		private String _proj4Text;
		private String _srText;
		private String _srTextEsri;

		public SpatialReferenceSystem(Integer srid) {
			this._srid = srid;
		}

		public SpatialReferenceSystem(Integer srid, String authorityName,
				Integer authoritySrid, String refSysName, String proj4Text,
				String srText, String srTextEsri) {
			this._srid = srid;
			this._authorityName = authorityName;
			this._authoritySrid = authoritySrid;
			this._refSysName = refSysName;
			this._proj4Text = proj4Text;
			this._srText = srText;
			this._srTextEsri = srTextEsri;
		}

		public Integer getSrid() {
			return this._srid;
		}

		public String getAuthorityName() {
			return this._authorityName;
		}

		public Integer getAuthoritySrid() {
			return this._authoritySrid;
		}

		public String getRefSysName() {
			return this._refSysName;
		}

		public String getProj4Text() {
			return this._proj4Text;
		}

		public String getSrText() {
			return this._srText;
		}

		public String getSrTextEsri() {
			return this._srTextEsri;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof SpatialReferenceSystem))
				return false;
			SpatialReferenceSystem other = (SpatialReferenceSystem) o;
			return (this._srid == other._srid);
		}

		public SpatialReferenceSystem equalsById(int srid) {
			if (this._srid == srid)
				return this;
			else
				return null;
		}

		public SpatialReferenceSystem equalsByEsriName(String esriName) {
			this._srTextEsri = this._srTextEsri == null ? "" : this._srTextEsri;
			if (this._srTextEsri.contains(esriName))
				return this;
			else
				return null;
		}

		@Override
		public int hashCode() {
			return this._srid;
		}
	}
}
