<head>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
  <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.16.0/dist/bootstrap-table.min.css">
  <link href="https://unpkg.com/multiple-select@1.5.2/dist/multiple-select.min.css" rel="stylesheet">
</head>

<div class="container">
  <h3>Bootstrap-table</h3>
  <h6>Detail-view with Sub-table</h6>
<table
  id="table"
  data-toggle="table"
  data-toolbar="#toolbar"
  data-filter-control="true"
  data-show-footer="false"
  data-detail-formatter="detailFormatter"
  data-detail-view="true"
  data-hide-unused-select-options="true">
  <thead>
    <tr>
      <th data-field="state" data-checkbox="true"></th>
      <th data-field="id" data-filter-control="select">Id</th>
      <th data-field="name" data-filter-control="select">Name</th>
      <th data-field="city" data-filter-control="select">City</th>
      <th data-field="age" data-filter-control="select" data-visible="false">Age</th>
    </tr>
  </thead>
</table>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/bootstrap-table@1.16.0/dist/bootstrap-table.min.js"></script>
<script src="https://unpkg.com/bootstrap-table@1.16.0/dist/extensions/filter-control/bootstrap-table-filter-control.min.js"></script>
<script src="https://unpkg.com/multiple-select@1.5.2/dist/multiple-select.min.js"></script>
<script src="js/funciones/jsDemo.js" type="text/javascript"></script>
