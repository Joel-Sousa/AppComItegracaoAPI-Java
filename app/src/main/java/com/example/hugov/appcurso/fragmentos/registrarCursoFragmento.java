package com.example.hugov.appcurso.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hugov.appcurso.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link registrarCursoFragmento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link registrarCursoFragmento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registrarCursoFragmento extends Fragment
//        implements Response.Listener<JSONObject>, Response.ErrorListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button buttonRegistrar;
    EditText editTextCodigo, editTextNome, editTextCategoria, editTextProfessor;
    ImageView imageViewFoto;
    ProgressDialog progressDialogProgress;
    RelativeLayout relativeLayoutRegistrar;
    RequestQueue requestQueueRequisicao;
    JsonObjectRequest jsonObjectRequestRequisicao;
    StringRequest stringRequestString;

    private OnFragmentInteractionListener mListener;

    public registrarCursoFragmento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registrarCursoFragmento.
     */
    // TODO: Rename and change types and number of parameters
    public static registrarCursoFragmento newInstance(String param1, String param2) {
        registrarCursoFragmento fragment = new registrarCursoFragmento();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_registrar_curso_fragmento, container,
                false);
        editTextCodigo = vista.findViewById(R.id.campoCodigo);
        editTextNome = vista.findViewById(R.id.campoNome);
        editTextCategoria = vista.findViewById(R.id.campoCategoria);
        editTextProfessor = vista.findViewById(R.id.campoProfessor);

        buttonRegistrar = vista.findViewById(R.id.btnRegistrar);

        requestQueueRequisicao = Volley.newRequestQueue(getContext());

            buttonRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    carregarApi();
                }
            });

        return vista;
    }
    private void carregarApi() {

            progressDialogProgress = new ProgressDialog(getContext());
            progressDialogProgress.setMessage("Carregando...");
            progressDialogProgress.show();

            String url = "http://192.168.0.104/apiWeb/registro.php?";
//                    "codigo="+editTextCodigo.getText().toString()+
//                    "&nome="+editTextNome.getText().toString()+
//                    "&categoria="+editTextCategoria.getText().toString()+
//                    "&professor="+editTextProfessor.getText().toString();

//            url = url.replace(" ", "%20");

//            jsonObjectRequestRequisicao= new JsonObjectRequest(Request.Method.GET, url, null,
//                    this, this);
//            requestQueueRequisicao.add(jsonObjectRequestRequisicao);

        stringRequestString = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialogProgress.hide();

                if (response.trim().equalsIgnoreCase("registra")) {
                    editTextCodigo.setText("");
                    editTextNome.setText("");
                    editTextCategoria.setText("");
                    editTextProfessor.setText("");

                    Toast.makeText(getContext(), "Registrado com sucesso", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getContext(), "Nao registrado com sucesso", Toast.LENGTH_SHORT).show();
                    Log.i("---", "---" + response);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Erro ao Registrar", Toast.LENGTH_SHORT).show();
                progressDialogProgress.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String codigo = editTextCodigo.getText().toString();
                String nome = editTextNome.getText().toString();
                String categoria = editTextCategoria.getText().toString();
                String professor = editTextProfessor.getText().toString();

                Map<String, String> dados = new HashMap<>();
                dados.put("codigo", codigo);
                dados.put("nome", nome);
                dados.put("categoria", categoria);
                dados.put("professor", professor);

                return dados;
            }

        };
        requestQueueRequisicao.add(stringRequestString);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    @Override
//    public void onErrorResponse(VolleyError volleyError) {
//    progressDialogProgress.hide();
//        Toast.makeText(getContext(), "Nao conectou ao servidor", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onResponse(JSONObject jsonObject) {
//        progressDialogProgress.hide();
//        Toast.makeText(getContext(), "Cadastrado!", Toast.LENGTH_SHORT).show();
//        editTextCodigo.setText("");
//        editTextNome.setText("");
//        editTextCategoria.setText("");
//        editTextProfessor.setText("");
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
