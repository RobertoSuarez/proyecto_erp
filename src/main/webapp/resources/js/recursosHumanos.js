/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//<![CDATA[
function generatePDF() {
    var doc = new jsPDF();
    var rolPagoElement = $('#dt-rolPago').html();
    var specialElement = {
        '#elementh': function (element, renderer) {
            return true;
        }
    };
    doc.fromHTML(rolPagoElement, 15, 15, {
        'width': 170,
        'elementHandlers': specialElement
    });
    doc.save('roldepago.pdf');
}
//]]>