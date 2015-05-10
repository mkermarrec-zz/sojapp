$(function() {

    // adding behaviour when clicking on choose file button
    $('#pictureButton').click(function(event) {
        // Prevent default.
        event.preventDefault();
        $('#pictureFile').click();
    });

    $('#pictureFile').change(function(event) {
        // Prevent default.
        event.preventDefault();
        $('#picture').val($(this).val());
        // retrieving data from form and creating a new one
        var data = new FormData();
        data.append("image", $('#pictureFile')[0].files[0]);
        data.append("token", $("input[name='authenticityToken']").val());
        $.ajax({
            url: cacheImageUrl,
            type: 'POST',
            data: data,
            contentType: false,
            processData: false,
            success : function (val){
                $('#icon-img').attr('src', showImageUrl + $("input[name='authenticityToken']").val()
                + '&' + Math.random());
            },
            error: function (jqXHR, textStatus, errorThrown) {
                var message = jqXHR.responseText;
                $('#uploadError').find('span').html(message);
                $('#uploadError').show();
            }
        });
    });

});