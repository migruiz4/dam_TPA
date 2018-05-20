package com.teleco.pruebatpa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class AdaptadorRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Variables
    private ArrayList<?> elementos;
    private int R_layout_IdView;
    private final OnItemClickListener listener;
    //Constructor de la clase

    public AdaptadorRecycler(ArrayList<?> items, int R_layout_IdView, OnItemClickListener listen) {
        this.elementos = new ArrayList<>();
        this.elementos = items;
        this.R_layout_IdView = R_layout_IdView;
        this.listener = listen;
    }

    //Métodos
    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        switch (R_layout_IdView){
            case R.layout.linea: return 0;
            case R.layout.modo: return 1;
            case R.layout.municipio: return 2;
            case R.layout.parada: return 3;
            case R.layout.servicio: return 4;
            case R.layout.horarios_linea: return 5;
            default: return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R_layout_IdView, parent, false);
        switch (viewType){
            case 0: return new ViewHolder0(view);
            case 1: return new ViewHolder1(view);
            case 2: return new ViewHolder2(view);
            case 3: return new ViewHolder3(view);
            case 4: return new ViewHolder4(view);
            case 5: return new ViewHolder5(view);
            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ((ViewHolder0) holder).bind(elementos.get(position), listener);
                ((ViewHolder0) holder).texto_linea.setText(((Linea)elementos.get(position)).getNombre());
                ((ViewHolder0) holder).texto_ID.setText(((Linea)elementos.get(position)).getIdLinea().toString());
                ((ViewHolder0) holder).texto_codigo.setText(((Linea)elementos.get(position)).getCodigo());
                ((ViewHolder0) holder).texto_operador.setText(((Linea)elementos.get(position)).getOperador());
                break;
            case 1:
                ((ViewHolder1) holder).bind(elementos.get(position), listener);
                ((ViewHolder1) holder).texto_modo.setText(((ModoTrans)elementos.get(position)).getDesc());
                ((ViewHolder1) holder).texto_ID.setText(((ModoTrans)elementos.get(position)).getIdModo().toString());

                String modo_transporte = ((ModoTrans)elementos.get(position)).getDesc();
                if (modo_transporte.equals("Autobús")) {
                    ((ViewHolder1) holder).foto_modo.setImageResource(R.drawable.icono_bus);
                }
                if (modo_transporte.equals("Metro")) {
                    ((ViewHolder1) holder).foto_modo.setImageResource(R.drawable.icono_metro);
                }
                if (modo_transporte.equals("Tranvía")) {
                    ((ViewHolder1) holder).foto_modo.setImageResource(R.drawable.icono_tranvia);
                }
                if (modo_transporte.equals("Tren Cercanías")) {
                    ((ViewHolder1) holder).foto_modo.setImageResource(R.drawable.icono_tren_cercanias);
                }
                if (modo_transporte.equals("Tren Media Distancia")) {
                    ((ViewHolder1) holder).foto_modo.setImageResource(R.drawable.icono_tren_media_distancia);
                }
                if (modo_transporte.equals("Bicicleta")) {
                    ((ViewHolder1) holder).foto_modo.setImageResource(R.drawable.icono_bicicleta);
                }
                if (modo_transporte.equals("Barco")) {
                    ((ViewHolder1) holder).foto_modo.setImageResource(R.drawable.icono_barco);
                }
                if (modo_transporte.equals("Bus+Bici")) {
                    ((ViewHolder1) holder).foto_modo.setImageResource(R.drawable.icono_bus_bici);
                }
                break;
            case 2:
                ((ViewHolder2) holder).bind(elementos.get(position), listener);
                ((ViewHolder2) holder).texto_municipio.setText(((Municipio)elementos.get(position)).getDatos());
                ((ViewHolder2) holder).texto_ID.setText(((Municipio)elementos.get(position)).getIdMunicipio().toString());
                break;
            case 3:
                ((ViewHolder3) holder).bind(elementos.get(position), listener);
                ((ViewHolder3) holder).texto_parada.setText(((Parada)elementos.get(position)).getNombre());
                ((ViewHolder3) holder).texto_ID.setText(((Parada)elementos.get(position)).getIdParada().toString());
                break;
            case 4:
                ((ViewHolder4) holder).bind(elementos.get(position), listener);
                ((ViewHolder4) holder).texto_linea.setText(((Servicio)elementos.get(position)).getNombre());
                ((ViewHolder4) holder).texto_ID.setText(((Servicio)elementos.get(position)).getIdLinea().toString());
                ((ViewHolder4) holder).texto_codigo.setText(((Servicio)elementos.get(position)).getCodigo());
                ((ViewHolder4) holder).texto_destino.setText(((Servicio)elementos.get(position)).getDestino());
                ((ViewHolder4) holder).texto_hora.setText(((Servicio)elementos.get(position)).getHora());
                break;
            case 5:
                ((ViewHolder5) holder).bind(elementos.get(position), listener);
                ((ViewHolder5) holder).horarios_linea.setText(((Horario)elementos.get(position)).getBloquesIda().toString());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }

    public static class ViewHolder0 extends RecyclerView.ViewHolder {

        public TextView texto_linea;
        public TextView texto_ID;
        public TextView texto_codigo;
        public TextView texto_operador;

        public ViewHolder0(View itemView) {
            super(itemView);
            texto_linea = (TextView) itemView.findViewById(R.id.id_linea_nom);
            texto_ID = (TextView) itemView.findViewById(R.id.linea_ID);
            texto_codigo = (TextView) itemView.findViewById(R.id.codigo_linea);
            texto_operador = (TextView) itemView.findViewById(R.id.id_operador_id);
        }
        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        public TextView texto_modo;
        public TextView texto_ID;
        public ImageView foto_modo;

        public ViewHolder1(View itemView) {
            super(itemView);
            texto_modo = (TextView) itemView.findViewById(R.id.id_modo_desc);
            texto_ID = (TextView) itemView.findViewById(R.id.modo_ID);
            foto_modo = (ImageView) itemView.findViewById(R.id.foto_modo);
        }
        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
    public static class ViewHolder2 extends RecyclerView.ViewHolder {

        public TextView texto_municipio;
        public TextView texto_ID;

        public ViewHolder2(View itemView) {
            super(itemView);
            texto_municipio = itemView.findViewById(R.id.id_municipio_nom);
            texto_ID = itemView.findViewById(R.id.municipio_ID);
        }
        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public static class ViewHolder3 extends RecyclerView.ViewHolder {

        public TextView texto_ID;
        public TextView texto_parada;


        public ViewHolder3(View itemView) {
            super(itemView);
            texto_parada = itemView.findViewById(R.id.id_parada_nom);
            texto_ID = itemView.findViewById(R.id.parada_ID);
        }

        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
    public static class ViewHolder4 extends RecyclerView.ViewHolder {

        public TextView texto_linea;
        public TextView texto_ID;
        public TextView texto_codigo;
        public TextView texto_destino;
        public TextView texto_hora;

        public ViewHolder4(View itemView) {
            super(itemView);
            texto_linea = (TextView) itemView.findViewById(R.id.nom_linea);
            texto_ID = (TextView) itemView.findViewById(R.id.id_lin);
            texto_codigo = (TextView) itemView.findViewById(R.id.codig_linea);
            texto_destino = (TextView) itemView.findViewById(R.id.destino);
            texto_hora = (TextView) itemView.findViewById(R.id.hora);

        }
        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public static class ViewHolder5 extends RecyclerView.ViewHolder {

        public TextView horarios_linea;

        public ViewHolder5(View itemView) {
            super(itemView);
            horarios_linea = (TextView) itemView.findViewById(R.id.informacion_linea);

        }
        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}