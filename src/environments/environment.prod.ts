export const environment = {
  production: true,
  apiUrl: 'http://100.122.8.47:8050/simrac/api',
  webUrl: 'web-url',
  initialLayers: [
    {
      id: 1,
      url: 'https://geocatmin.ingemmet.gob.pe/arcgis/rest/services/SERV_GEOLOGIA_100K/MapServer',
      disabled: true,
      name: 'Geología 100k',
    },
    {
      id: 2,
      url: 'https://gisem.osinergmin.gob.pe/serverdc/rest/services/DSGN/SCADA/MapServer',
      disabled: true,
      name: 'SCADA',
    },
    {
      id: 3,
      url: 'https://gisem.osinergmin.gob.pe/serverdc/rest/services/DSGN/TDGN/MapServer',
      disabled: true,
      name: 'TDGN',
    },
    {
      id: 4,
      url: 'https://geocatmin.ingemmet.gob.pe/arcgis/rest/services/SERV_CATASTRO_MINERO_WGS84/MapServer',
      disabled: false,
      name: 'Catastro Minero',
    },

    {
      id: 5,
      url: 'https://gisem.osinergmin.gob.pe/serverdc/rest/services/DSGN/ADMGSP/MapServer',
      disabled: false,
      name: 'Infraestructura',
    },
  ],
};
