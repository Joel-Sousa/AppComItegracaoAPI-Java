package com.example.hugov.appcurso.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hugov.appcurso.R;
import com.example.hugov.appcurso.adaptador.CursosAdapter;
import com.example.hugov.appcurso.entidades.Curso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link consultarListaCursosFragmento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link consultarListaCursosFragmento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class consultarListaCursosFragmento extends Fragment
        implements Response.Listener<JSONObject>, Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerViewCursos;
    ArrayList<Curso> listaCursos;
    ProgressDialog progressDialogProgresso;
    RequestQueue requestQueueRequisicao;
    JsonObjectRequest jsonObjectRequestRequest;

    private OnFragmentInteractionListener mListener;

    public consultarListaCursosFragmento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment consultarListaCursosFragmento.
     */
    // TODO: Rename and change types and number of parameters
    public static consultarListaCursosFragmento newInstance(String param1, String param2) {
        consultarListaCursosFragmento fragment = new consultarListaCursosFragmento();
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
        View vista = inflater.inflate(R.layout.fragment_consultar_lista_cursos_fragmento, container, false);

        listaCursos = new ArrayList<>();

        recyclerViewCursos = (RecyclerView) vista.findViewById(R.id.idRecycler);
        recyclerViewCursos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewCursos.setHasFixedSize(true);

        requestQueueRequisicao = Volley.newRequestQueue(getContext());

        carregarApi();

        return vista;
    }

    private void carregarApi() {
        progressDialogProgresso = new ProgressDialog(getContext());
        progressDialogProgresso.setMessage("Carregando...");
        progressDialogProgresso.show();

        String url = "http://192.168.0.104/apiWeb/consultarLista.php";

        jsonObjectRequestRequest= new JsonObjectRequest(Request.Method.GET, url, null,
                this, this);
        requestQueueRequisicao.add(jsonObjectRequestRequest);
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

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        progressDialogProgresso.hide();
        Toast.makeText(getContext(), "Nao conectou" , Toast.LENGTH_SHORT).show();
        Log.e("--->Error", volleyError.toString());
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        progressDialogProgresso.hide();

        Curso curso = null;
        JSONArray json = jsonObject.optJSONArray("curso");

        try {
            for(int i=0; i<json.length(); i++){
                curso = new Curso();
                JSONObject jsonObject1 = null;
                jsonObject1 = json.getJSONObject(i);

                curso.setCodigo(jsonObject1.optInt("codigo"));
                curso.setNome(jsonObject1.optString("nome"));
                curso.setCategoria(jsonObject1.optString("categoria"));
                curso.setProfessor(jsonObject1.optString("professor"));
                listaCursos.add(curso);
            }

            progressDialogProgresso.hide();
            CursosAdapter adapter = new CursosAdapter(listaCursos);
            recyclerViewCursos.setAdapter(adapter);

        }catch(JSONException e){
            e.printStackTrace();
            progressDialogProgresso.hide();
            Toast.makeText(getContext(), "Nnao listou os cursos" + jsonObject, Toast.LENGTH_SHORT).show();
            Log.e("--> error:", jsonObject.toString());
            Log.e("--> error:", e.toString());
        }
    }

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
