package com.example.hugov.appcurso.adaptador;

import static java.security.AccessController.getContext;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hugov.appcurso.R;
import com.example.hugov.appcurso.entidades.Curso;

import java.util.List;

public class CursosAdapter extends RecyclerView.Adapter<CursosAdapter.CursosHolder> {

    List<Curso> listaCursos;
    public CursosAdapter(List<Curso> listaCursos){
        this.listaCursos = listaCursos;
    }

    @NonNull
    @Override
    public CursosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_cursos,
                viewGroup, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        return new CursosHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CursosHolder cursosHolder, int i) {

        cursosHolder.txtCodigo.setText(listaCursos.get(i).getCodigo().toString());
        cursosHolder.txtNome.setText(listaCursos.get(i).getNome().toString());
        cursosHolder.txtCategoria.setText(listaCursos.get(i).getCategoria().toString());
        cursosHolder.txtProfessor.setText(listaCursos.get(i).getProfessor().toString());

    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

    public class CursosHolder extends RecyclerView.ViewHolder {

        TextView txtCodigo, txtNome, txtCategoria, txtProfessor;

        public CursosHolder(View itemView) {
            super(itemView);
            txtCodigo = (TextView) itemView.findViewById(R.id.txtCodigo);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtCategoria = (TextView) itemView.findViewById(R.id.txtCategoria);
            txtProfessor = (TextView) itemView.findViewById(R.id.txtProfessor);
        }
    }
}
