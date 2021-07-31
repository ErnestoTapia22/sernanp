export const environment = {
  production: true,
  apiUrl: 'url-api',
  webUrl: 'web-url',
  initialLayers: [
    {
      id: 1,
      url:
        'https://geocatmin.ingemmet.gob.pe/arcgis/rest/services/SERV_GEOLOGIA_100K/MapServer',
      disabled: false,
    },
    {
      id: 2,
      url:
        'https://gisem.osinergmin.gob.pe/serverdc/rest/services/DSGN/SCADA/MapServer',
      disabled: true,
    },
    {
      id: 3,
      url:
        'https://gisem.osinergmin.gob.pe/serverdc/rest/services/DSGN/TDGN/MapServer',
      disabled: true,
    },
  ],
};
