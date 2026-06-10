from django.shortcuts import redirect, render, get_object_or_404
from .models import Note
from .forms import NoteForm
# Create your views here.

def note_list(request):
    notes = Note.objects.all()
    return render(request, 'notes/list.html', {'notes': notes})

def create(request):
    form = NoteForm(request.POST or None)
    if form.is_valid():
        form.save()
        return redirect('notes')
    
    return render(request, 'notes/form.html', {
        'form': form,
        'form_title': 'Create Note'
    })

def edit(request, id):
    notes = get_object_or_404(Note, id=id)
    form = NoteForm(request.POST or None, instance=notes)
    if form.is_valid():
        form.save()
        return redirect('notes')
    return render(request, 'notes/form.html', {
        'form': form,
        'form_title': 'Edit notes'
    })
    
def delete(request, id):
    notes = get_object_or_404(Note, id=id)
    if request.method == 'POST':
        notes.delete()
        return redirect('notes')
    return render(request, 'notes/confirm_delete.html', {'notes': notes})