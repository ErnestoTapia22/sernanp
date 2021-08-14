// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080',
  webUrl: 'web-url',
  initialLayers: [
    {
      id: 1,
      url:
        'https://geocatmin.ingemmet.gob.pe/arcgis/rest/services/SERV_GEOLOGIA_100K/MapServer',
      disabled: true,
      name: 'Geología 100k',
    },
    {
      id: 2,
      url:
        'https://gisem.osinergmin.gob.pe/serverdc/rest/services/DSGN/SCADA/MapServer',
      disabled: true,
      name: 'SCADA',
    },
    {
      id: 3,
      url:
        'https://gisem.osinergmin.gob.pe/serverdc/rest/services/DSGN/TDGN/MapServer',
      disabled: true,
      name: 'TDGN',
    },
    {
      id: 4,
      url:
        'https://geocatmin.ingemmet.gob.pe/arcgis/rest/services/SERV_CATASTRO_MINERO_WGS84/MapServer',
      disabled: false,
      name: 'Catastro Minero',
    },
    {
      id: 5,
      url:
        'https://gisem.osinergmin.gob.pe/serverdc/rest/services/DSGN/ADMGSP/MapServer',
      disabled: true,
      name: 'Infraestructura',
    },
  ],
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
