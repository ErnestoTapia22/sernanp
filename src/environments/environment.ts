// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl2: 'http://desarrollo.sernanp.gob.pe:28081/ws_simrac/api',
  apiUrl3: 'http://100.122.8.47:8060/simrac/api',
  apiUrl: 'http://100.122.8.47:8060/simrac/api',
  webUrl: 'http://localhost:4200/',
  authUrl: 'http://desarrollo.sernanp.gob.pe:28081/api-lanp/oauth/token',
  authCredentials: 'dj-sernanp-app:@re34n@tur4l',
  externalServices: [
    {
      agreement: [
        {
          name: 'Servicio Rest ANP + ZR',
          url: 'http://desarrollo.sernanp.gob.pe:28081/api-lanp/anps/listAll',
          disabled: false,
        },
        {
          name: 'Servicio Rest ACR',
          url: 'http://desarrollo.sernanp.gob.pe:28081/api-lanp/acr/listAll',
          disabled: true,
        },
        {
          name: 'Servicio Rest ACP',
          url: 'http://desarrollo.sernanp.gob.pe:28081/api-lanp/acp/listAll',
          disabled: true,
        },
      ],
    },
  ],
  initialLayers: [
    {
      id: 5,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/representatividad/peru_sernanp_010200/MapServer',
      disabled: true,
      name: 'Áreas de Administración Nacional',
    },
    {
      id: 6,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/representatividad/peru_sernanp_010203/MapServer',
      disabled: false,
      name: 'Áreas de Administración Regional',
    },
    {
      id: 7,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/representatividad/peru_sernanp_010204/MapServer',
      disabled: false,
      name: 'Áreas de Administración Privada',
    },
    {
      id: 1,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000501/MapServer',
      disabled: false,
      name: 'Límite Departamental',
    },
    {
      id: 2,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000502/MapServer',
      disabled: false,
      name: 'Límite Provincial',
    },
    {
      id: 3,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000503/MapServer',
      disabled: false,
      name: 'Límite Distrital',
    },
    {
      id: 4,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000504/MapServer',
      disabled: false,
      name: 'Capitales',
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
