export const environment = {
  production: true,
  apiUrl: 'http://desarrollo.sernanp.gob.pe:28081/ws_simrac/api',
  apiUrl2: 'http://100.122.8.47:8060/simrac/api',
  webUrl: 'http://localhost:4200/',
  authUrl: 'http://desarrollo.sernanp.gob.pe:28081/api-lanp/oauth/token',
  logOutUrl: 'http://desarrollo.sernanp.gob.pe:28081/dianaac/Salir.action',
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
    }
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
