// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
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

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
