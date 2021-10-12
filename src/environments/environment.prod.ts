export const environment = {
  production: true,
  apiUrl2: 'http://desarrollo.sernanp.gob.pe:28081/ws_simrac/api',
  apiUrl: 'http://100.122.8.47:8060/ws_simrac/api',
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
      url2: 'https://geoservicios.sernanp.gob.pe/desarrollo/rest/services/ac/Acuerdo_Conservacion/MapServer',
      url: 'http://desarrollo.sernanp.gob.pe:6080/arcgis/rest/services/sernanp_ac/cbs_ac_ambito/MapServer',
      disabled: false,
      name: 'Acuerdos de Conservación',
      visible:true
    },
    {
      id: 1,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/representatividad/peru_sernanp_010200/MapServer',
      disabled: false,
      name: 'Áreas de Naturales Protegidas',
      visible:true
    },
    {
      id: 2,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/representatividad/peru_sernanp_010203/MapServer',
      disabled: false,
      name: 'Áreas de Conservación Regional',
      visible:true
    },
    {
      id: 3,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/representatividad/peru_sernanp_010204/MapServer',
      disabled: false,
      name: 'Áreas de Conservación Privada',
      visible:true
    },
    {
      id: 4,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000501/MapServer',
      disabled: false,
      name: 'Límite Departamental',
      visible:true
    },
    {
      id: 5,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000502/MapServer',
      disabled: false,
      name: 'Límite Provincial',
      visible:true
    },
    {
      id: 6,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000503/MapServer',
      disabled: false,
      name: 'Límite Distrital',
      visible:true
    },
    {
      id: 7,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_inei_000504/MapServer',
      disabled: false,
      name: 'Capitales',
      visible:true
    },
    {
      id: 8,
      url: 'https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_peru/peru_cultura_000201/MapServer',
      disabled: false,
      name: 'Comunidades',
      visible:false
    }
  ],
  conservationAgreements: [
    {
      name: 'Ámbito de Acuerdo de Conservación Poligono',
      url2: 'https://geoservicios.sernanp.gob.pe/desarrollo/rest/services/ac/Acuerdo_Conservacion/FeatureServer/0',
      url: 'http://desarrollo.sernanp.gob.pe:6080/arcgis/rest/services/sernanp_ac/cbs_ac_ambito/FeatureServer/0',
      disabled: false,
    },
    {
      name: 'Ámbito de Acuerdo de Conservación Punto',
      url2: 'https://geoservicios.sernanp.gob.pe/desarrollo/rest/services/ac/Acuerdo_Conservacion/FeatureServer/1',
      url: 'http://desarrollo.sernanp.gob.pe:6080/arcgis/rest/services/sernanp_ac/cbs_ac_ambito/FeatureServer/0',
      disabled: false,
    }
  ],
  searchWidget:[
    {
      url:"https://geoservicios.sernanp.gob.pe/arcgis/rest/services/sernanp_visor/sernanp_busqueda/MapServer/0",
      url2:"http://desarrollo.sernanp.gob.pe:6080/arcgis/rest/services/sernanp_visor/sernanp_busqueda/MapServer/0"
    },
    {
      url2:"https://geoservicios.sernanp.gob.pe/desarrollo/rest/services/ac/Acuerdo_Conservacion/MapServer/0",
      url:"http://desarrollo.sernanp.gob.pe:6080/arcgis/rest/services/sernanp_ac/cbs_ac_ambito/MapServer/0"
    }
  ]
};
