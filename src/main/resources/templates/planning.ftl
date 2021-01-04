<!-- ####### https://html-online.com/editor/ #########
https://divtable.com/table-styler/
-->
<html>

<head>
  <style>
    table.blueTable {
      border: 1px solid #1C6EA4;
      background-color: #EEEEEE;
      width: 60%;
      text-align: left;
      border-collapse: collapse;
    }

    table.blueTable td,
    table.blueTable th {
      border: 1px solid #AAAAAA;
      padding: 3px 2px;
    }

    table.blueTable tbody td {
      font-size: 13px;
    }

    table.blueTable tr:nth-child(even) {
      background: #D0E4F5;
    }

    table.blueTable thead {
      background: #1C6EA4;
      background: -moz-linear-gradient(top, #5592bb 0%, #327cad 66%, #1C6EA4 100%);
      background: -webkit-linear-gradient(top, #5592bb 0%, #327cad 66%, #1C6EA4 100%);
      background: linear-gradient(to bottom, #5592bb 0%, #327cad 66%, #1C6EA4 100%);
      border-bottom: 2px solid #444444;
    }

    table.blueTable thead th {
      font-size: 15px;
      font-weight: bold;
      color: #FFFFFF;
      border-left: 2px solid #D0E4F5;
    }

    table.blueTable thead th:first-child {
      border-left: none;
    }

    table.blueTable tfoot {
      font-size: 14px;
      font-weight: bold;
      color: #FFFFFF;
      background: #D0E4F5;
      background: -moz-linear-gradient(top, #dcebf7 0%, #d4e6f6 66%, #D0E4F5 100%);
      background: -webkit-linear-gradient(top, #dcebf7 0%, #d4e6f6 66%, #D0E4F5 100%);
      background: linear-gradient(to bottom, #dcebf7 0%, #d4e6f6 66%, #D0E4F5 100%);
      border-top: 2px solid #444444;
    }

    table.blueTable tfoot td {
      font-size: 14px;
    }

    table.blueTable tfoot .links {
      text-align: right;
    }

    table.blueTable tfoot .links a {
      display: inline-block;
      background: #1C6EA4;
      color: #FFFFFF;
      padding: 2px 8px;
      border-radius: 5px;
    }
  </style>
</head>

<body>
  <h1 style="color: #5e9ca0;">
    <img style="float: left;" src="https://html-online.com/img/6-table-div-html.png" alt="html table div" width="45" />Planning des formations
  </h1>

  <p>Bonjour ${formateur.nom} ${formateur.prenom},</p>
  <p>Vous trouverez ci-après votre planning des formations pour les jours à venir.  </p>


  <table class="blueTable" >
    <thead>
      <tr>
        <th style="width: 208.85px;">Formation</th>
        <th style="width: 75px;">Date de d&eacute;but</th>
        <th style="width: 75px;">Date de fin</th>
      </tr>
    </thead>
    <tbody>
    <#list seances as item>
      <tr>
        <td style="width: 208.85px;">${item.libelleFormation}</td>
        <td>${item.dateDebutSeance}</td>
        <td>${item.dateFinSeance}</td>
      </tr>
    </#list>
    </tbody>
  </table>

  <p>Bien à vous.  </p>
</body>

</html>