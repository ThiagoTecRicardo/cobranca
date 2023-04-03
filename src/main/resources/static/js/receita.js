$('#confirmacaoExclusaoReceitaModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var codigoReceita = button.data('codigo');
	var descricaoReceita = button.data('descricao');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + codigoReceita);
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir a receita <strong>' + descricaoReceita + '</strong>?');
});