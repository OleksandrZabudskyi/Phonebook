$(document).ready(function () {
    $("#myBtnCreate").click(function () {
        $('#addContact').on('hidden.bs.modal', function (e) {
            $(this)
                .find("input,textarea,text")
                .val('')
                .end();
        })
        $("#myModalCreate").modal();
    });

    $("#myBtnEdit").click(function () {
        $("#myModalEdit").modal();
    });

    $('#myTable').on('click', '.clickable-row', function (event) {
        $(this).addClass('active').siblings().removeClass('active');
        /*  var value = $(this).data('id');*/
        $("#deleteForm #id").val($(this).data('id'));
        $("#editContact #id").val($(this).data('id'));
        $("#editContact #firstName").val($(this).data('fn'));
        $("#editContact #lastName").val($(this).data('ln'));
        $("#editContact #additionalName").val($(this).data('an'));
        $("#editContact #mobilePhone").val($(this).data('mp'));
        $("#editContact #homePhone").val($(this).data('hp'));
        $("#editContact #address").val($(this).data('ad'));
        $("#editContact #email").val($(this).data('em'));
        //   $(".modal-body #email").val( $(this).data('em'));
        $(".panel-title").removeClass('has-error');
    });

});
