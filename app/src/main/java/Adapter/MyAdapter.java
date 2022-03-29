package Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tecbank.R;
import java.util.ArrayList;
/*
Se incia la clase MyAdapter para mostrar la información que esta en la base de datos y así
mostrarla en el RyclerView
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList nombre, tipo, monto;
    public MyAdapter(Context context, ArrayList nombre, ArrayList tipo, ArrayList monto) {
        this.context = context;
        this.nombre = nombre;
        this.tipo = tipo;
        this.monto = monto;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nombre.setText(String.valueOf(nombre.get(position)));
        holder.tipo.setText(String.valueOf(tipo.get(position)));
        holder.monto.setText(String.valueOf(monto.get(position)));
    }
    @Override
    public int getItemCount() {
        return nombre.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre,tipo,monto;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textname);
            tipo = itemView.findViewById(R.id.textmovimiento);
            monto = itemView.findViewById(R.id.textmonto);
        }
    }
}
