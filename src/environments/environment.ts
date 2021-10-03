// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl2: 'http://desarrollo.sernanp.gob.pe:28081/ws_simrac/api',
  apiUrl: 'http://desarrollo.sernanp.gob.pe:28081/ws_simrac/api',
  apiUrl3: 'http://localhost:8050/simrac/api',
  webUrl: 'http://localhost:4200/',
  authUrl: 'http://desarrollo.sernanp.gob.pe:28081/api-lanp/oauth/token',
  logOutUrl: '/default/login',
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
      id: 0,
      url: 'https://geoservicios.sernanp.gob.pe/desarrollo/rest/services/ac/Acuerdo_Conservacion/MapServer',
      disabled: false,
      name: 'Acuerdos de Conservación',
    },
    {
      id: 1,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/representatividad/peru_sernanp_010200/MapServer',
      disabled: false,
      name: 'Áreas de Naturales Protegidas',
    },
    {
      id: 2,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/representatividad/peru_sernanp_010203/MapServer',
      disabled: false,
      name: 'Áreas de Conservación Regional',
    },
    {
      id: 3,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/representatividad/peru_sernanp_010204/MapServer',
      disabled: false,
      name: 'Áreas de Conservación Privada',
    },
    {
      id: 4,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000501/MapServer',
      disabled: false,
      name: 'Límite Departamental',
    },
    {
      id: 5,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000502/MapServer',
      disabled: false,
      name: 'Límite Provincial',
    },
    {
      id: 6,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000503/MapServer',
      disabled: false,
      name: 'Límite Distrital',
    },
    {
      id: 7,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000504/MapServer',
      disabled: false,
      name: 'Capitales',
    },
    {
      id: 8,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_cultura_000201/MapServer',
      disabled: false,
      name: 'Comunidades',
    },
  ],
  conservationAgreements: [
    {
      name: 'Ámbito de Acuerdo de Conservación',
      url: 'https://geoservicios.sernanp.gob.pe/desarrollo/rest/services/ac/Acuerdo_Conservacion/FeatureServer/0',
      disabled: false,
    },
    {
      name: 'Vigilancia y control',
      url: 'https://geoservicios.sernanp.gob.pe/desarrollo/rest/services/ac/Acuerdo_Conservacion/FeatureServer/1',
      disabled: false,
    },
    {
      name: 'Restauración',
      url: 'https://geoservicios.sernanp.gob.pe/desarrollo/rest/services/ac/Acuerdo_Conservacion/FeatureServer/2',
      disabled: false,
    },
    {
      name: 'Ámbito productivo',
      url: 'https://geoservicios.sernanp.gob.pe/desarrollo/rest/services/ac/Acuerdo_Conservacion/FeatureServer/3',
      disabled: false,
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
